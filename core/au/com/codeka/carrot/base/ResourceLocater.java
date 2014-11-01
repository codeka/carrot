package au.com.codeka.carrot.base;

import java.io.IOException;
import java.io.Reader;

/**
 * Used to resolve resource names to actual files.
 * <p>
 * While conceptually file-based, this could actually be implemented on top of anything.
 */
public interface ResourceLocater {

  /** Gets the name of the directory the given file is in. */
  public String getDirectory(String fullName) throws IOException;

  /**
   * Searches for a resource with the given name, returns the full path to it.
   * <p>
   * This will search for the resource in the given path first, if not found there will search a
   * locator-specific search path for the resource before giving up.
   *
   * @param path The (optional) path to search for the resource in.
   * @param name The name of the resource to search file.
   * @return The full path to the resource.
   * @throws IOException If the resource cannot be found.
   */
  public String findResource(String path, String name) throws IOException;

  /**
   * Searches for a resource with the given name, returns the full path to it.
   *
   * @param name The name of the resource to search file.
   * @return The full path to the resource.
   * @throws IOException If the resource cannot be found.
   */
  public String findResource(String relativeName) throws IOException;

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
   * @param resourceName The name of the resource, as returned from {@link #findResource}.
   * @return A value indicating the 'last modified time' (or a hash) of the resource.
   * @throws IOException
   */
  public long getModifiedTime(String resourceName) throws IOException;

  /**
   * Gets a {@link Reader} to read the contents of the given resource.
   *
   * @param resourceName Name of the resource, typically you will use {@link #findResource} to find
   *   the full path to the resource first and use that here.
   * @return A {@link Reader} for reading the contents of the resource.
   * @throws IOException
   */
  public Reader getReader(String resourceName) throws IOException;

  /**
   * Gets the contents of the resource, as a string.
   *
   * @param resourceName Name of the resource, typically you will use {@link #findResource} to find
   *   the full path to the resource first and use that here.
   * @return The string contents of the resource.
   * @throws IOException
   */
  public String getString(String resourceName) throws IOException;
}
