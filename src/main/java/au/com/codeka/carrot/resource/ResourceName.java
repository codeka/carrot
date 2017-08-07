package au.com.codeka.carrot.resource;

import javax.annotation.Nullable;

/**
 * The "name" of a resolved resource (usually a file, but not nessecarily). You can pass this to a resource locator
 * to get the actual contents of the resource.
 * <p>
 * <p>{@link ResourceName}s may be related to a parent {@link ResourceName}. They may not necessarily be direct
 * children of the parent, in which case children will always be separated by a forward slash '/'.
 */
public interface ResourceName {

  /**
   * @return The name of this resource, which may be relative to the parent (i.e. not necessarily a direct child of the
   * parent). Relative names are always separated with forward slash '/'.
   */
  String getName();

  /**
   * @return The parent {@link ResourceName} (may be null if this {@link ResourceName} is relative to the root).
   */
  @Nullable
  ResourceName getParent();
}
