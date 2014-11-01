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
