package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;

/**
 * {@link Bindings} without any values.
 *
 * @author Marten Gajda
 */
public final class EmptyBindings implements Bindings {
  @Override
  public Object resolve(@Nonnull String key) {
    return null;
  }

  @Override
  public boolean isEmpty() {
    return true;
  }
}
