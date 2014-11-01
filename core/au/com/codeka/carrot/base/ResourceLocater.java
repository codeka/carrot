package au.com.codeka.carrot.base;

import java.io.IOException;
import java.io.Reader;

public interface ResourceLocater {

  public String getFullName(String relativeName, String relativeDir, String defaultDir)
      throws IOException;

  public String getFullName(String relativeName, String defaultDir)
      throws IOException;

  public String getDirectory(String fullName) throws IOException;

  public Reader getReader(String fullName, String encoding) throws IOException;

  public String getString(String fullName, String encoding) throws IOException;
}
