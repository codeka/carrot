package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * {@link Bindings} based on the content of a {@link Map}.
 */
public final class MapBindings implements Bindings, Iterable<EntryBindings> {
  private final Map<String, Object> values;

  public static Builder newBuilder() {
    return new Builder();
  }

  public MapBindings(Map<String, Object> values) {
    this.values = values;
  }

  @Override
  public Object resolve(@Nonnull String key) {
    return values.get(key);
  }

  @Override
  public boolean isEmpty() {
    return values.isEmpty();
  }

  @Override
  public Iterator<EntryBindings> iterator() {
    final Iterator<Map.Entry<String, Object>> iterator = values.entrySet().iterator();
    return new Iterator<EntryBindings>() {
      @Override
      public boolean hasNext() {
        return iterator.hasNext();
      }

      @Override
      public EntryBindings next() {
        return new EntryBindings(iterator.next());
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("This iterator does not support remove");
      }
    };
  }

  public static class Builder {
    private final Map<String, Object> values = new TreeMap<>();

    public Builder set(String key, Object value) {
      values.put(key, value);
      return this;
    }

    public MapBindings build() {
      return new MapBindings(values);
    }
  }
}
