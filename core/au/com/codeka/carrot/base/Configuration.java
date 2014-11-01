package au.com.codeka.carrot.base;

import java.io.File;
import java.util.Locale;
import java.util.TimeZone;

import au.com.codeka.carrot.cache.SynchronousStorage;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.lib.FilterLibrary;
import au.com.codeka.carrot.lib.Importable;
import au.com.codeka.carrot.lib.Macro;
import au.com.codeka.carrot.lib.MacroLibrary;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.lib.TagLibrary;
import au.com.codeka.carrot.util.Log;

public class Configuration {
  private String encoding;
  private Locale locale;
  private TimeZone timezone;
  private String workspace;
  private ResourceLocater resourceLocater;
  private Class<?> parseCacheClass;
  private Logger logger;

  protected Configuration() {
    resourceLocater = new FileLocater();
    parseCacheClass = SynchronousStorage.class;
    logger = new Log.DefaultLogger();
  };

  public static void addImport(Importable importee) {
    if (importee instanceof Filter) {
      FilterLibrary.addFilter((Filter) importee);
    } else if (importee instanceof Tag) {
      TagLibrary.addTag((Tag) importee);
    } else if (importee instanceof Macro) {
      MacroLibrary.addMacro((Macro) importee);
    } else {
      // TODO: this method shouldn't be static and it should be on the Application class anyway.
    }
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public String getEncoding() {
    return encoding;
  }

  public Locale getLocale() {
    return locale;
  }

  public TimeZone getTimezone() {
    return timezone;
  }

  public void setTimezone(TimeZone timezone) {
    this.timezone = timezone;
  }

  public String getWorkspace() {
    return workspace;
  }

  public void setWorkspace(String rootPath) {
    if (rootPath == null)
      return;
    if (rootPath.endsWith(File.separator)) {
      workspace = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
    } else {
      workspace = rootPath;
    }
  }

  public void setResourceLocater(ResourceLocater resourceLocater) {
    this.resourceLocater = resourceLocater;
  }

  public ResourceLocater getResourceLocater() {
    return resourceLocater;
  }

  public void setParseCacheClass(Class<?> clazz) {
    parseCacheClass = clazz;
  }

  public Class<?> getParseCacheClass() {
    return parseCacheClass;
  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  public Logger getLogger() {
    return logger;
  }

  public interface Logger {
    public static final int WARNING = 2;
    public static final int INFO = 1;
    public static final int DEBUG = 0;

    void write (int level, String msg);
  }
}
