package au.com.codeka.carrot.lib.filter;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class RandomFilter implements Filter {

  @SuppressWarnings("unchecked")
  @Override
  public Object filter(Object object, JangodInterpreter interpreter, String... arg)
      throws InterpretException {
    if (object == null) {
      return null;
    }
    // collection
    if (object instanceof Collection) {
      Collection<?> clt = (Collection<?>) object;
      Iterator<?> it = clt.iterator();
      int size = clt.size();
      if (size == 0) {
        return null;
      }
      int index = Double.valueOf(Math.random() * size).intValue();
      if (index == size)
        index = 0;
      while (index-- > 0) {
        it.next();
      }
      return it.next();
    }
    // array
    if (object.getClass().isArray()) {
      int size = Array.getLength(object);
      if (size == 0) {
        return null;
      }
      int index = Double.valueOf(Math.random() * size).intValue();
      if (index == size)
        index = 0;
      return Array.get(object, index);
    }
    // map
    if (object instanceof Map) {
      Map<?, ?> map = (Map<?, ?>) object;
      Iterator<?> it = map.values().iterator();
      int size = map.size();
      if (size == 0) {
        return null;
      }
      int index = Double.valueOf(Math.random() * size).intValue();
      if (index == size)
        index = 0;
      while (index-- > 0) {
        it.next();
      }
      return it.next();
    }
    // number
    if (object instanceof Number) {
      return BigDecimal.valueOf(((Number) object).doubleValue() * Math.random()).longValue();
    }
    // string
    if (object instanceof String) {
      try {
        return BigDecimal.valueOf(Double.valueOf((String) object).doubleValue() * Math.random())
            .longValue();
      } catch (Exception e) {
        return 0;
      }
    }
    // TODO iterable
    // TODO iterator
    return object;
  }

  @Override
  public String getName() {
    return "filter";
  }

}
