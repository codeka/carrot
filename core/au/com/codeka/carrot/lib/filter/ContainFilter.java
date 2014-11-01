package au.com.codeka.carrot.lib.filter;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.base.Constants;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.util.ObjectStringEqual;

public class ContainFilter implements Filter {
  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException {
    if (object == null) {
      return false;
    }
    if (arg.length != 1) {
      throw new InterpretException("filter contain expects 1 arg >>> " + arg.length);
    }
    Object argObj;
    boolean isNull = false;
    if (arg[0].startsWith(Constants.STR_SINGLE_QUOTE)
        || arg[0].startsWith(Constants.STR_DOUBLE_QUOTE)) {
      argObj = arg[0].substring(1, arg[0].length() - 1);
    } else {
      argObj = interpreter.retraceVariable(arg[0]);
      if (isNull = argObj == null) {
        argObj = arg[0];
      }
    }
    // iterable
    if (object instanceof Iterable) {
      Iterator<?> it = ((Iterable<?>) object).iterator();
      return iteratorContain(it, isNull, argObj);
    }
    // array
    if (object.getClass().isArray()) {
      int length = Array.getLength(object);
      Object item;
      for (int i = 0; i < length; i++) {
        item = Array.get(object, i);
        if (item == null) {
          if (isNull)
            return true;
        } else if (ObjectStringEqual.evaluate(item, argObj)) {
          return true;
        }
      }
      return false;
    }
    // map
    if (object instanceof Map) {
      Iterator<?> it = ((Map<?, ?>) object).values().iterator();
      return iteratorContain(it, isNull, argObj);
    }
    // string
    if (object instanceof String) {
      return object.toString().contains(Objects.toString(argObj));
    }
    // iterator
    if (object instanceof Iterator) {
      return iteratorContain((Iterator<?>) object, isNull, argObj);
    }
    throw new InterpretException("Filter contain can't be applied to: "
        + object.getClass().getName());
  }

  @Override
  public String getName() {
    return "contain";
  }

  private boolean iteratorContain(Iterator<?> it, boolean isNull, Object argObj) {
    Object item;
    while (it.hasNext()) {
      item = it.next();
      if (item == null) {
        if (isNull) {
          return true;
        }
      }
      else if (ObjectStringEqual.evaluate(item, argObj)) {
        return true;
      }
    }
    return false;
  }

}
