package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;

/**
 * {@link Bindings} based on the content of a {@link Map}
 *
 * @author Marten Gajda
 */
public final class MapBindings implements Bindings, Iterable {
  private final Map<String, Object> contextMap;

  public MapBindings(Map<String, Object> contextMap) {
    this.contextMap = contextMap;
  }

  @Override
  public Object resolve(@Nonnull String key) {
    return contextMap.get(key);
  }

  @Override
  public boolean isEmpty() {
    return contextMap.isEmpty();
  }

  @Override
  public Iterator iterator() {
    final Iterator<Map.Entry<String, Object>> iterator = contextMap.entrySet().iterator();
    return new Iterator() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public Object next() {
        return new EntryBindings(iterator.next());
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("This iterator does not support remove");
      }
    };
  }
}
