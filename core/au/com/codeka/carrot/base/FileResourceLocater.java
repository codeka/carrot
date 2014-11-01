package au.com.codeka.carrot.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/** Implementation of {@link ResourceLocater} that loads files from the file system. */
public class FileResourceLocater implements ResourceLocater {
  private final Configuration config;
  private final File baseFile;

  public FileResourceLocater(Configuration config, String basePath) {
    this.config = config;
    this.baseFile = new File(basePath);
  }

  @Override
  public String getDirectory(String fullName) throws IOException {
    File file = new File(fullName);
    if (file.isFile()) {
      return file.getParentFile().getCanonicalPath();
    }
    return file.getCanonicalPath();
  }

  @Override
  public String findResource(String path, String name) throws IOException {
    if (path != null) {
      File file = new File(new File(path), name);
      if (file.exists() && file.isFile()) {
        return file.getCanonicalPath();
      }
    }

    File file = new File(name);
    if (file.exists() && file.isFile()) {
      return file.getCanonicalPath();
    }

    file = new File(baseFile, name);
    if (file.exists() && file.isFile()) {
      return file.getCanonicalPath();
    }

    throw new FileNotFoundException("[path = " + path + "] [name = " + name
        + "] [base = " + baseFile + "]");
  }

  @Override
  public String findResource(String name) throws IOException {
    return findResource(null, name);
  }

  @Override
  public long getModifiedTime(String resourceName) throws IOException {
    return new File(resourceName).lastModified();
  }

  @Override
  public Reader getReader(String resourceName) throws IOException {
    File file = new File(resourceName);
    return new InputStreamReader(new FileInputStream(file), config.getEncoding());
  }

  @Override
  public String getString(String resourceName) throws IOException {
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
}
