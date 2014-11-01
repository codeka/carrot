package au.com.codeka.carrot.base;

import static au.com.codeka.carrot.util.logging.JangodLogger;

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

public class Configuration {
  private String encoding;
  private Locale locale;
  private TimeZone timezone;
  private String workspace;
  private ResourceLocater resourceLocater;
  private Class<?> parseCacheClass;

  protected Configuration() {
    resourceLocater = new FileLocater();
    parseCacheClass = SynchronousStorage.class;
  };

  public static void addImport(Importable importee) {
    if (importee instanceof Filter) {
      FilterLibrary.addFilter((Filter) importee);
    } else if (importee instanceof Tag) {
      TagLibrary.addTag((Tag) importee);
    } else if (importee instanceof Macro) {
      MacroLibrary.addMacro((Macro) importee);
    } else {
      if (importee != null)
        JangodLogger.warning("Can't recognize the importing object >>> "
            + importee.getClass().getName());
      else
        JangodLogger.warning("Can't import null object");
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
}
