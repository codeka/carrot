package au.com.codeka.carrot.resource;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;

import javax.annotation.Nullable;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * A simple {@link ResourceLocator} that just keeps stuff in memory.
 */
public class MemoryResourceLocator implements ResourceLocator {
  private final Map<String, String> resources;

  /**
   * Constructs a new {@link MemoryResourceLocator} using the given {@link Configuration} and base path to search for
   * resources in.
   *
   * @param resources A mapping of strings to resources.
   */
  public MemoryResourceLocator(Map<String, String> resources) {
    this.resources = resources;
  }

  @Override
  public ResourceName findResource(@Nullable ResourceName parent, String name) throws CarrotException {
    if (parent != null) {
      // TODO: handle parents
    }

    return new MemoryResourceName(null, name);
  }

  @Override
  public ResourceName findResource(String name) throws CarrotException {
    return findResource(null, name);
  }

  @Override
  public long getModifiedTime(ResourceName resourceName) throws CarrotException {
    return 0;
  }

  @Override
  public Reader getReader(ResourceName resourceName) throws CarrotException {
    String name = ((MemoryResourceName) resourceName).name;
    String resource = resources.get(name);
    if (resource == null) {
      throw new CarrotException(String.format("File not found: %s", name));
    }

    return new StringReader(resources.get(name));
  }

  /**
   * A builder for {@link MemoryResourceLocator}.
   */
  public static class Builder implements ResourceLocator.Builder {
    private final Map<String, String> resources;

    public Builder() {
      this(new TreeMap<String, String>());
    }

    public Builder(Map<String, String> resources) {
      this.resources = resources;
    }

    public Builder add(String name, String value) {
      resources.put(name, value);
      return this;
    }

    @Override
    public ResourceLocator build(Configuration config) {
      return new MemoryResourceLocator(resources);
    }
  }

  /** Our version of {@link ResourceName} that represents file system files. */
  private static class MemoryResourceName extends AbstractResourceName {
    private final String name;

    public MemoryResourceName(@Nullable ResourceName parent, String name) {
      super(parent, name);
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public ResourceName getParent() {
      // TODO: implement me.
      return null;
    }

    @Override
    public String toString() {
      return name;
    }

    @Override
    public int hashCode() {
      return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
      return other instanceof MemoryResourceName && ((MemoryResourceName) other).name.equals(name);
    }
  }
}
