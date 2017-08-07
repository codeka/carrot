package au.com.codeka.carrot.resource;

import javax.annotation.Nullable;

/**
 * Abstract implementation of a {@link ResourceName}.
 */
public abstract class AbstractResourceName implements ResourceName {
  protected final String name;
  @Nullable
  protected final ResourceName parent;

  public AbstractResourceName(@Nullable ResourceName parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  /**
   * @return The name of this resource, which may be relative to the parent (i.e. not necessarily a direct child of the
   * parent). Relative names are always separated with forward slash '/'.
   */
  @Override
  public String getName() {
    return name;
  }

}
