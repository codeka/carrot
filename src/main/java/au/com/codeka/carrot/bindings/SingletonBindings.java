package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;

/**
 * {@link Bindings} with exactly one value.
 *
 * @author Marten Gajda
 */
public final class SingletonBindings implements Bindings, Iterable<EntryBindings> {
  private final String key;
  private final Object value;

  public SingletonBindings(@Nonnull String key, @Nullable Object value) {
    this.key = key;
    this.value = value;
  }

  @Override
  public Object resolve(@Nonnull String key) {
    return this.key.equals(key) ? value : null;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public Iterator<EntryBindings> iterator() {
    return Collections.singleton(new EntryBindings(key, value)).iterator();
  }
}
