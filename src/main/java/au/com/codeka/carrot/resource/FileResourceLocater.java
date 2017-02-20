package au.com.codeka.carrot.resource;

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
   * @param config The {@link Configuration} you used to construct the {@link au.com.codeka.carrot.TemplateEngine}.
   * @param basePath The path path to search for resources in.
   */
  public FileResourceLocater(Configuration config, String basePath) {
    this.config = config;
    this.baseFile = new File(basePath);
  }

  @Override
  public ResourceName findResource(@Nullable ResourceName parent, String name) throws IOException {
    if (parent != null) {
      File file = new File(((FileResourceName) parent).getFile(), name);
      if (file.exists() && file.isFile()) {
        return new FileResourceName(parent, name, file);
      }
    }

    File file = new File(baseFile, name);
    if (file.exists() && file.isFile()) {
      return new FileResourceName(null, name, file);
    }

    throw new FileNotFoundException("[parent = " + parent + "] [name = " + name + "] [base = " + baseFile + "]");
  }

  @Override
  public ResourceName findResource(String name) throws IOException {
    return findResource(null, name);
  }

  @Override
  public long getModifiedTime(ResourceName resourceName) throws IOException {
    return ((FileResourceName) resourceName).getFile().lastModified();
  }

  @Override
  public Reader getReader(ResourceName resourceName) throws IOException {
    return new InputStreamReader(
        new FileInputStream(((FileResourceName) resourceName).getFile()), config.getEncoding());
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