package au.com.codeka.carrot.lib.filter;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class LengthFilter implements Filter {
  @Override
  public Object filter(Object object, JangodInterpreter interpreter, String... arg)
      throws InterpretException {
    if (null == object) {
      return 0;
    }

    if (object instanceof Collection) {
      return ((Collection<?>) object).size();
    }

    if (object.getClass().isArray()) {
      return Array.getLength(object);
    }

    if (object instanceof Map) {
      return ((Map<?, ?>) object).size();
    }

    if (object instanceof Iterable) {
      Iterator<?> it = ((Iterable<?>) object).iterator();
      int size = 0;
      while (it.hasNext()) {
        it.next();
        size++;
      }
      return size;
    }

    if (object instanceof Iterator) {
      Iterator<?> it = (Iterator<?>) object;
      int size = 0;
      while (it.hasNext()) {
        it.next();
        size++;
      }
      return size;
    }

    if (object instanceof String) {
      return ((String) object).length();
    }
    return 0;
  }

  @Override
  public String getName() {
    return "length";
  }

}
