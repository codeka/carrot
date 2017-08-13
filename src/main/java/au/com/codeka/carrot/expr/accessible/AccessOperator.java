package au.com.codeka.carrot.expr.accessible;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.binary.BinaryOperator;
import org.dmfs.iterables.decorators.Filtered;
import org.dmfs.iterators.filters.Skip;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * The accessor operator like in {@code a.b} or {@code a[b]}. It tries to access the key, index, field or method {@code b} of object {@code a}.
 *
 * @author Marten Gajda
 */
public final class AccessOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return access(left, right.value());
  }

  private Object access(Object value, Object accessor) throws CarrotException {
    if (value instanceof Map) {
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
    } else if (value instanceof Iterable && Number.class.isAssignableFrom(accessor.getClass())) {
      // provide indexed access to Iterables
      // beware, for large Iterables this can be very slow
      return new Filtered<>((Iterable) value, new Skip<>(ValueHelper.toNumber(accessor).intValue())).iterator().next();
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
        throw new CarrotException(e);
      }

      //   throw new CarrotException("Cannot access key '" + accessor + "' in '" + value + "'");
    }
  }
}
