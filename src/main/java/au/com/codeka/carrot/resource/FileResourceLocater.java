package au.com.codeka.carrot.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.annotation.Nullable;

import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.base.Constants;

/** Implementation of {@link ResourceLocater} that loads files from the file system. */
public class FileResourceLocater implements ResourceLocater {
  private final Configuration config;
  private final File baseFile;

  public FileResourceLocater(Configuration config, String basePath) {
    this.config = config;
    this.baseFile = new File(basePath);
  }

  @Override
  public ResourceName findResource(ResourceName parent, String name) throws IOException {
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

    throw new FileNotFoundException("[parent = " + parent + "] [name = " + name
        + "] [base = " + baseFile + "]");
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

  @Override
  public String getString(ResourceName resourceName) throws IOException {
    Reader reader = getReader(resourceName);
    BufferedReader br = new BufferedReader(reader);
    StringBuffer buff = new StringBuffer();
    String line;
    try {
      while ((line = br.readLine()) != null) {
        buff.append(line);
        buff.append(Constants.STR_NEW_LINE);
      }
    } finally {
      reader.close();
    }
    return buff.toString();
  }

  /** Our version of {@link ResourceName} that represents file system files. */
  private static class FileResourceName extends ResourceName {
    private File file;

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
      return other instanceof FileResourceName
          && ((FileResourceName) other).file.equals(file);
    }
  }
}
