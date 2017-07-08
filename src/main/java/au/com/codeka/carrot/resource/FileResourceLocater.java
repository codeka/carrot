package au.com.codeka.carrot.resource;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;

import javax.annotation.Nullable;
import java.io.*;

/** An implementation of {@link ResourceLocater} that loads files from the file system. */
public class FileResourceLocater implements ResourceLocater {
  private final Configuration config;
  private final File baseFile;

  /**
   * Constructs a new {@link FileResourceLocater} using the given {@link Configuration} and base path to search for
   * resources in.
   *
   * @param config The {@link Configuration} you used to construct the {@link CarrotEngine}.
   * @param basePath The path path to search for resources in.
   */
  public FileResourceLocater(Configuration config, String basePath) {
    this.config = config;
    this.baseFile = new File(basePath);
  }

  @Override
  public ResourceName findResource(@Nullable ResourceName parent, String name) throws CarrotException {
    File file = new File(name);
    if (file.isAbsolute()) {
      return new FileResourceName(null, file.getName(), file);
    }

    if (parent != null) {
      file = new File(((FileResourceName) parent).getFile(), name);
      if (file.exists() && file.isFile()) {
        return new FileResourceName(parent, name, file);
      }
    }

    file = new File(baseFile, name);
    if (file.exists() && file.isFile()) {
      return new FileResourceName(null, name, file);
    }

    throw new CarrotException(
        new FileNotFoundException("[parent = " + parent + "] [name = " + name + "] [base = " + baseFile + "]"));
  }

  @Override
  public ResourceName findResource(String name) throws CarrotException {
    return findResource(null, name);
  }

  @Override
  public long getModifiedTime(ResourceName resourceName) throws CarrotException {
    return ((FileResourceName) resourceName).getFile().lastModified();
  }

  @Override
  public Reader getReader(ResourceName resourceName) throws CarrotException {
    try {
      return new InputStreamReader(
          new FileInputStream(((FileResourceName) resourceName).getFile()), config.getEncoding());
    } catch (IOException e) {
      throw new CarrotException(e);
    }
  }

  /** Our version of {@link ResourceName} that represents file system files. */
  private static class FileResourceName extends ResourceName {
    private final File file;

    public FileResourceName(@Nullable ResourceName parent, String name, File file) {
      super(parent, name);
      this.file = file;
    }

    public File getFile() {
      return file;
    }

    @Override
    public ResourceName getParent() {
      File parent = file.getParentFile();
      return new FileResourceName(null, parent.getName(), parent);
    }

    @Override
    public String toString() {
      return file.getAbsolutePath();
    }

    @Override
    public int hashCode() {
      return file.hashCode();
    }

    @Override
    public boolean equals(Object other) {
      return other instanceof FileResourceName && ((FileResourceName) other).file.equals(file);
    }
  }
}