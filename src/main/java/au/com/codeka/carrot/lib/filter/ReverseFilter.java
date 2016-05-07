package au.com.codeka.carrot.lib.filter;

import java.lang.reflect.Array;
import java.util.Collection;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class ReverseFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws InterpretException {
    if (object == null) {
      return null;
    }
    // collection
    if (object instanceof Collection) {
      Object[] origin = ((Collection<?>) object).toArray();
      int length = origin.length;
      Object[] res = new Object[length];
      length--;
      for (int i = 0; i <= length; i++) {
        res[i] = origin[length - i];
      }
      return res;
    }
    // array
    if (object.getClass().isArray()) {
      int length = Array.getLength(object);
      Object[] res = new Object[length];
      length--;
      for (int i = 0; i <= length; i++) {
        res[i] = Array.get(object, length - i);
      }
      return res;
    }
    // string
    if (object instanceof String) {
      String origin = (String) object;
      int length = origin.length();
      char[] res = new char[length];
      length--;
      for (int i = 0; i <= length; i++) {
        res[i] = origin.charAt(length - i);
      }
      return String.valueOf(res);
    }

    throw new InterpretException("Filter cannot be applied to " + object.getClass().getName());
  }

  @Override
  public String getName() {
    return "reverse";
  }

}
