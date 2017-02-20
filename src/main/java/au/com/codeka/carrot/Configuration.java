package au.com.codeka.carrot;

import au.com.codeka.carrot.lib.TagRegistry;

/**
 * The {@link Configuration} is used to configure various aspects of the carrot engine.
 */
public class Configuration {
  private String encoding;
  private TagRegistry tagRegistry;

  public Configuration() {
    encoding = "UTF-8";
    tagRegistry = new TagRegistry();
  }

  public String getEncoding() {
    return encoding;
  }

  public Configuration setEncoding(String encoding) {
    this.encoding = encoding;
    return this;
  }

  public TagRegistry getTagRegistry() {
    return tagRegistry;
  }
}
