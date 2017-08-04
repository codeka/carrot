package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.Bindings;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * {@link Bindings} based on the content of a {@link Map}
 *
 * @author Marten Gajda
 */
public final class MapBindings implements Bindings {
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
}
