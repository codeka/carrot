package au.com.codeka.carrot;

import au.com.codeka.carrot.lib.TagRegistry;
import au.com.codeka.carrot.resource.FileResourceLocater;
import au.com.codeka.carrot.resource.ResourceLocater;

/**
 * The {@link Configuration} is used to configure various aspects of the carrot engine.
 */
public class Configuration {
  private String encoding;
  private ResourceLocater resourceLocater;
  private TagRegistry tagRegistry;
  private Logger logger;

  public Configuration() {
    encoding = "UTF-8";
    resourceLocater = new FileResourceLocater(this, ".");
    tagRegistry = new TagRegistry();
  }

  public String getEncoding() {
    return encoding;
  }

  public Configuration setEncoding(String encoding) {
    this.encoding = encoding;
    return this;
  }

  public ResourceLocater getResourceLocater() {
    return resourceLocater;
  }

  public Configuration setResourceLocater(ResourceLocater resourceLocater) {
    this.resourceLocater = resourceLocater;
    return this;
  }

  public TagRegistry getTagRegistry() {
    return tagRegistry;
  }

  public Configuration setLogger(Logger logger) {
    this.logger = logger;
    return this;
  }

  public Logger getLogger() {
    return logger;
  }

  public interface Logger {
    int LEVEL_DEBUG = 1;
    int LEVEL_INFO = 2;
    int LEVEL_WARNING = 3;

    void print(int level, String msg);
  }
}
