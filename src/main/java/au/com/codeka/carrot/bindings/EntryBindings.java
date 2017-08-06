package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * {@link Bindings} for {@link java.util.Map.Entry}.
 * <p>
 * It provides access to key and value of an Entry like so
 * <pre><code>
 *    {{ item.key }} -&gt; {{ item.value }}
 * </code></pre>
 * <p>
 * Note: strictly spoken this would not be necessary, because the reflection function resolution would do the
 * same (by calling {@link Map.Entry#getKey()} and {@link Map.Entry#getValue()}. A dedicated {@link Bindings} class is,
 * however, considered better design and certainly faster.
 *
 * @author Marten Gajda
 */
public final class EntryBindings implements Bindings, Iterable<Object> {
  private final Map.Entry<String, Object> entry;


  public EntryBindings(String key, Object value) {
    this(new AbstractMap.SimpleImmutableEntry<>(key, value));
  }

  public EntryBindings(Map.Entry<String, Object> entry) {
    this.entry = entry;
  }

  @Override
  public Object resolve(@Nonnull String key) {
    switch (key) {
      case "key":
        return entry.getKey();
      case "value":
        return entry.getValue();
      default:
        return null;
    }
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public Iterator<Object> iterator() {
    return Arrays.asList(entry.getKey(), entry.getValue()).iterator();
  }
}
