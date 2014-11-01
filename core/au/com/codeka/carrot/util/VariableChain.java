package au.com.codeka.carrot.util;

import static au.com.codeka.carrot.util.logging.JangodLogger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import au.com.codeka.carrot.util.logging.Level;

public class VariableChain {

  static final String GET = "get";
  static final String IS = "is";

  private List<String> chain;
  private Object value;

  public VariableChain(List<String> chain, Object value) {
    this.chain = chain;
    this.value = value;
  }

  public Object resolve() {
    for (String name : chain) {
      if (value == null) {
        return null;
      } else {
        value = resolveInternal(name);
      }
    }
    return value;
  }

  @SuppressWarnings("unchecked")
  private Object resolveInternal(String name) {
    // field
    Class<?> clazz = value.getClass();
    try {
      Field field = clazz.getDeclaredField(name);
      return field.get(value);
    } catch (Exception e1) {
      // method
      Method mth1 = null;
      try {
        mth1 = clazz.getDeclaredMethod(name);
      } catch (NoSuchMethodException e) {
        String uname = upperFirst(name);
        try {
          mth1 = clazz.getDeclaredMethod(GET + uname);
          // mth1.setAccessible(array, flag)
        } catch (NoSuchMethodException e2) {
          try {
            mth1 = clazz.getDeclaredMethod(IS + uname);
          } catch (NoSuchMethodException e3) {
            // nothing;
          }
        }
      } catch (SecurityException e) {
        // nothing
      }
      if (mth1 != null) {
        try {
          return mth1.invoke(value);
        } catch (Exception e) {
          JangodLogger.log(Level.SEVERE, "resolve variable trigger error.", e.getCause());
        }
      }
    }

    // map
    if (value instanceof Map) {
      Map<?, ?> map = (Map<?, ?>) value;
      if (map.containsKey(name)) {
        return map.get(name);
      }
    }

    try {
      int index = Integer.parseInt(name);
      // array
      if (value.getClass().isArray()) {
        return Array.get(value, index);
      }
      // list
      if (value instanceof List) {
        return ((List<?>) value).get(index);
      }
      // collection
      if (value instanceof Collection) {
        return ((Collection<?>) value).toArray()[index];
      }
    } catch (Exception e) {
      // nothing;
    }

    return null;
  }

  private String upperFirst(String name) {
    char c = name.charAt(0);
    if (Character.isLowerCase(c)) {
      return String.valueOf(c).toUpperCase().concat(name.substring(1));
    } else {
      return name;
    }
  }

}
