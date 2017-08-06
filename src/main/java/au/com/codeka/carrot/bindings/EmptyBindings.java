package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Iterator;

/**
 * {@link Bindings} without any values.
 *
 * @author Marten Gajda
 */
public final class EmptyBindings implements Bindings, Iterable<EntryBindings> {
  @Override
  public Object resolve(@Nonnull String key) {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }

  @Override
  public Iterator<EntryBindings> iterator() {
    return Collections.<EntryBindings>emptyList().iterator();
  }
}
