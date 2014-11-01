package au.com.codeka.carrot.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class ObjectIterator {

  @SuppressWarnings("unchecked")
  public static ForLoop getLoop(Object obj) {
    if (obj == null) {
      return new ForLoop(new ArrayList<Object>().iterator(), 0);
    }
    // collection
    if (obj instanceof Collection) {
      Collection<?> clt = (Collection<?>) obj;
      return new ForLoop(clt.iterator(), clt.size());
    }
    // array
    if (obj.getClass().isArray()) {
      return new ForLoop(new ArrayIterator(obj), Array.getLength(obj));
    }
    // map
    if (obj instanceof Map) {
      Collection<?> clt = ((Map<?, ?>) obj).values();
      return new ForLoop(clt.iterator(), clt.size());
    }
    // iterable,iterator
    if (obj instanceof Iterable) {
      // TODO fix the loop if length is unknown.
      return new ForLoop(((Iterable<?>) obj).iterator());
    }
    if (obj instanceof Iterator) {
      // TODO fix the loop if length is unknown.
      return new ForLoop((Iterator<?>) obj);
    }
    // others
    ArrayList<Object> res = new ArrayList<Object>();
    res.add(obj);
    return new ForLoop(res.iterator(), 1);
  }

}
