package au.com.codeka.carrot.resource;

import java.io.IOException;
import java.io.Reader;

/**
 * Used to resolve resource names to actual files.
 * <p>
 * While conceptually file-based, this could actually be implemented on top of anything.
 */
public interface ResourceLocater {
  /**
   * Searches for a resource with the given name, returns the full path to it.
   * <p>
   * This will search for the resource in the given path first, if not found there will search a
   * locator-specific search path for the resource before giving up.
   *
   * @param parent The parent to look for the resource in. If not found here, we will also do a
   *     search for a non-relative name.
   * @param name The name of the resource to search file.
   * @return A {@link ResourceName} representing the resolved name of the resource.
   * @throws IOException If the resource cannot be found.
   */
  public ResourceName findResource(ResourceName parent, String name) throws IOException;

  /**
   * Searches for a resource with the given name, returns the full path to it.
   *
   * @param name The name of the resource to search file.
   * @return A {@link ResourceName} representing the resolved name of the resource.
   * @throws IOException If the resource cannot be found.
   */
  public ResourceName findResource(String name) throws IOException;

  /**
   * Gets a value which indicates when the resource was modified. This could be a timestamp, but
   * it could also be a hash, the actual value doesn't matter as long as it's different when the
   * resource is modified.
   * <p>
   * The value is used to determine whether we need to flush our caches, and this function will be
   * called for every request to render a template, so calling this needs to be quick.
   * <p>
   * Zero indicates this {@link ResourceLocater} doesn't support modification detection.
   *
   * @param resourceName The {@link ResourceName} of the resource.
   * @return A value indicating the 'last modified time' (or a hash) of the resource.
   * @throws IOException
   */
  public long getModifiedTime(ResourceName resourceName) throws IOException;

  /**
   * Gets a {@link Reader} to read the contents of the given resource.
   *
   * @param resourceName The {@link ResourceName} of the resource.
   * @return A {@link Reader} for reading the contents of the resource.
   * @throws IOException
   */
  public Reader getReader(ResourceName resourceName) throws IOException;

  /**
   * Gets the contents of the resource, as a string.
   *
   * @param resourceName The {@link ResourceName} of the resource.
   * @return The string contents of the resource.
   * @throws IOException
   */
  public String getString(ResourceName resourceName) throws IOException;
}
