package au.com.codeka.carrot.expr.accessible;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.binary.BinaryOperator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The accessor operator like in {@code a.b} or {@code a[b]}. It tries to access the key, index,
 * field or method {@code b} of object {@code a}.
 */
public final class AccessOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return access(left, right.value());
  }

  private Object access(Object value, Object accessor) throws CarrotException {
    if (value == null) {
      return null;
    } else if (value instanceof Map) {
      Map map = (Map) value;
      return map.get(accessor);
    } else if (value instanceof Bindings) {
      Bindings bindings = (Bindings) value;
      return bindings.resolve(accessor.toString());
    } else if (value instanceof List) {
      List list = (List) value;
      return list.get(ValueHelper.toNumber(accessor).intValue());
    } else if (value.getClass().isArray()) {
      return Array.get(value, ValueHelper.toNumber(accessor).intValue());
    } else if (value instanceof Iterable) {
      long index = ValueHelper.toNumber(accessor).longValue();
      long i = index;
      Iterator iter = ((Iterable) value).iterator();
      while (i > 0 && iter.hasNext()) {
        i--;
        iter.next();
      }
      if (i == 0) {
        return iter.next();
      }
      throw new CarrotException(
          String.format("Index [%d] out of bounds by %d elements", index, i));
    } else {
      // Do some reflection. First, check for a field with the given name.
      try {
        String name = accessor.toString();
        Field field = value.getClass().getField(name);
        field.setAccessible(true);
        return field.get(value);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        // Just keep trying.
      }

      // Next, try a method with the given name
      try {
        String name = accessor.toString();
        Method method = value.getClass().getMethod(name);
        method.setAccessible(true);
        return method.invoke(value);
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        // Just keep trying.
      }

      // Next, try a getter method with the given name (that is, if name is "foo" try "getFoo").
      try {
        String name = accessor.toString();
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        name = "get" + name;
        Method method = value.getClass().getMethod(name);
        method.setAccessible(true);
        return method.invoke(value);
      } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
        // Just keep trying.
      }

      throw new CarrotException("Cannot access key '" + accessor + "' in '" + value + "'");
    }
  }
}
