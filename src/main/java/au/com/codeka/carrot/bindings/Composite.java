package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;
import java.util.Arrays;


/**
 * Composite {@link Bindings} which combines the values of other {@link Bindings} into one. Note that the order is
 * important in case two bindings contain a value with the same key. In such case the first binding wins.
 *
 * <p>To avoid conflicts, you can insert "prefix" sub-bindings like so
 *
 * <pre>{@code
 * new Composite(
 *    new MapBindings(map),
 *    new SingletonBindings("$json", new JsonObjectBindings(jsonObject));
 * )
 * }</pre>
 *
 * <p>Given that the key {@code "key"} exists in both, the {@code map} and the {@code jsonObject} you would access
 * them like:
 *
 * <pre><code>
 * Map key: {{ key }}
 * JSON key: {{ $json.key }}
 * </code></pre>
 */
public final class Composite implements Bindings {
  private final Iterable<Bindings> bindingsIterable;

  public Composite(Bindings... bindings) {
    this(Arrays.asList(bindings));
  }

  public Composite(Iterable<Bindings> bindingsIterable) {
    this.bindingsIterable = bindingsIterable;
  }

  @Override
  public Object resolve(@Nonnull String key) {
    for (Bindings bindings : bindingsIterable) {
      Object value = bindings.resolve(key);
      if (value != null) {
        return value;
      }
    }
    return null;
  }

  @Override
  public boolean isEmpty() {
    for (Bindings bindings : bindingsIterable) {
      if (!bindings.isEmpty()) {
        return false;
      }
    }
    return true;
  }
}
