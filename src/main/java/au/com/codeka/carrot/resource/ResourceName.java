package au.com.codeka.carrot.resource;

import javax.annotation.Nullable;

/**
 * Represents a resolved resource name. You can pass this to resource locater to get the actual
 * resource.
 */
public abstract class ResourceName {
  protected final String name;
  @Nullable protected final ResourceName parent;

  public ResourceName(@Nullable ResourceName parent, String name) {
    this.parent = parent;
    this.name = name;
  }

  /**
   * @return The name of this resource, which may be relative to the parent (i.e. not necessarily a
   * direct child of the parent).
   */
  public String getName() {
    return name;
  }

  /**
   * @return The direct 'parent' of this resource. This will resolve the name of this resource and
   * then return the direct parent of the resolved name.
   */
  public abstract ResourceName getParent();
}
