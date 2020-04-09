package au.com.codeka.carrot.util;

import java.util.ArrayList;
import java.util.Iterator;

public class CollectionHelper {
  /** Converts the given {@link Iterable} to an {@link ArrayList}. */
  public static <T> ArrayList<T> toArrayList(Iterable<T> it) {
    if (it instanceof ArrayList) {
      return (ArrayList<T>) it;
    }

    return toArrayList(it.iterator());
  }

  /** Converts the given {@link Iterator} to an {@link ArrayList}. */
  public static <T> ArrayList<T> toArrayList(Iterator<T> it) {
    ArrayList<T> list = new ArrayList<>();
    while (it.hasNext()) {
      list.add(it.next());
    }
    return list;
  }
}
