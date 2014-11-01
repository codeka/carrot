package au.com.codeka.carrot.util;

import java.math.BigDecimal;
import java.math.BigInteger;

import au.com.codeka.carrot.base.Constants;

public class ObjectStringEqual {

  public static boolean evaluate(Object object, Object strObj) {
    if (object == null) {
      return strObj == null;
    } else {
      if (strObj == null)
        return false;
      if (String.class.isAssignableFrom(strObj.getClass())) {
        String str = (String) strObj;
        if (object instanceof String) {
          return str.equals(object);
        }

        if (object instanceof Integer) {
          try {
            return Integer.valueOf(str).equals(object);
          } catch (Exception e) {
            return false;
          }
        }

        if (object instanceof Long) {
          try {
            return Long.valueOf(str).equals(object);
          } catch (Exception e) {
            return false;
          }
        }

        if (object instanceof Boolean) {
          if ((Boolean) object) {
            return str.equalsIgnoreCase(Constants.STR_TRUE);
          } else {
            return str.equalsIgnoreCase(Constants.STR_FALSE);
          }
        }

        if (object instanceof Float) {
          try {
            return Float.valueOf(str).equals(object);
          } catch (Exception e) {
            return false;
          }
        }

        if (object instanceof Short) {
          try {
            return Short.valueOf(str).equals(object);
          } catch (Exception e) {
            return false;
          }
        }

        if (object instanceof Double) {
          try {
            return Double.valueOf(str).equals(object);
          } catch (Exception e) {
            return false;
          }
        }

        if (object instanceof Byte) {
          try {
            return Byte.valueOf(str).equals(object);
          } catch (Exception e) {
            return false;
          }
        }

        if (object instanceof BigInteger) {
          try {
            return Long.valueOf(str).longValue() == ((BigInteger) object).longValue();
          } catch (Exception e) {
            return false;
          }
        }

        if (object instanceof BigDecimal) {
          try {
            return Double.valueOf(str).doubleValue() == ((BigDecimal) object).doubleValue();
          } catch (Exception e) {
            return false;
          }
        }

        // TODO suppost more type

        return str.equals(object);
      } else {
        return object.equals(strObj);
      }
    }
  }
}
