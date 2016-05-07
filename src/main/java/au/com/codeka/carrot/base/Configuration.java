package au.com.codeka.carrot.base;

import java.util.Locale;
import java.util.TimeZone;

import au.com.codeka.carrot.lib.FilterLibrary;
import au.com.codeka.carrot.lib.MacroLibrary;
import au.com.codeka.carrot.lib.TagLibrary;
import au.com.codeka.carrot.resource.FileResourceLocater;
import au.com.codeka.carrot.resource.ResourceLocater;
import au.com.codeka.carrot.util.Log;

public class Configuration {
  private String encoding;
  private Locale locale;
  private TimeZone timezone;
  private ResourceLocater resourceLocater;
  private boolean parseCacheEnabled;
  private Logger logger;
  private FilterLibrary filterLibrary;
  private TagLibrary tagLibrary;
  private MacroLibrary macroLibrary;

  protected Configuration() {
    resourceLocater = new FileResourceLocater(this, ".");
    parseCacheEnabled = true;
    logger = new Log.DefaultLogger();
    filterLibrary = new FilterLibrary(this);
    tagLibrary = new TagLibrary(this);
    macroLibrary = new MacroLibrary(this);
  };

  public FilterLibrary getFilterLibrary() {
    return filterLibrary;
  }

  public MacroLibrary getMacroLibrary() {
    return macroLibrary;
  }

  public TagLibrary getTagLibrary() {
    return tagLibrary;
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

  public void setResourceLocater(ResourceLocater resourceLocater) {
    this.resourceLocater = resourceLocater;
  }

  public ResourceLocater getResourceLocater() {
    return resourceLocater;
  }

  public void setParseCacheEnabled(boolean enabled) {
    parseCacheEnabled = enabled;
  }

  public boolean isParseCacheEnabled() {
    return parseCacheEnabled;
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
