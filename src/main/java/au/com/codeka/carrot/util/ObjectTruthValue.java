package au.com.codeka.carrot.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class ObjectTruthValue {

  public static boolean evaluate(Object object) {
    if (object == null) {
      return false;
    }

    if (object instanceof Boolean) {
      Boolean b = (Boolean) object;
      return b.booleanValue();
    }

    if (object instanceof Number) {
      return ((Number) object).intValue() != 0;
    }

    if (object instanceof String) {
      return !object.toString().equals("");
    }

    if (object.getClass().isArray()) {
      return Array.getLength(object) != 0;
    }

    if (object instanceof Collection) {
      return ((Collection<?>) object).size() != 0;
    }

    if (object instanceof Map) {
      return ((Map<?, ?>) object).size() != 0;
    }

    return true;
  }
}
