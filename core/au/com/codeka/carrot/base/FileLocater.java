package au.com.codeka.carrot.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileLocater implements ResourceLocater {

  @Override
  public String getDirectory(String fullName) throws IOException {
    File file = new File(fullName);
    if (file.isFile()) {
      return file.getParentFile().getCanonicalPath();
    }
    return file.getCanonicalPath();
  }

  @Override
  public String getFullName(String relativeName, String relativeDir,
      String defaultDir) throws IOException {
    File file = new File(relativeDir + File.separator + relativeName);
    if (file.exists() && file.isFile()) {
      return file.getCanonicalPath();
    }
    file = new File(relativeName);
    if (file.exists() && file.isFile()) {
      return file.getCanonicalPath();
    }
    file = new File(defaultDir + File.separator + relativeName);
    if (file.exists() && file.isFile()) {
      return file.getCanonicalPath();
    }
    throw new IOException("File not found >>> '" + relativeName + "' in "
        + relativeDir + " and " + defaultDir);
  }

  @Override
  public String getFullName(String relativeName, String defaultDir)
      throws IOException {
    File file = new File(relativeName);
    if (file.exists() && file.isFile()) {
      return file.getCanonicalPath();
    }
    file = new File(defaultDir + File.separator + relativeName);
    if (file.exists() && file.isFile()) {
      return file.getCanonicalPath();
    }
    throw new IOException("File not found >>> '" + relativeName + "' in "
        + defaultDir);
  }

  @Override
  public Reader getReader(String fullName, String encoding) throws IOException {
    File file = new File(fullName);
    if (file.exists() && file.isFile()) {
      return new InputStreamReader(new FileInputStream(file), encoding);
    }
    throw new IOException("File not found >>> " + fullName);
  }

  @Override
  public String getString(String fullName, String encoding) throws IOException {
    Reader reader = getReader(fullName, encoding);
    BufferedReader br = new BufferedReader(reader);
    StringBuffer buff = new StringBuffer();
    String line;
    try {
      while ((line = br.readLine()) != null) {
        buff.append(line);
        buff.append(Constants.STR_NEW_LINE);
      }
    } catch (IOException e) {
      throw e;
    } finally {
      reader.close();
    }
    return buff.toString();
  }

}
