package au.com.codeka.carrot;

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
  private boolean autoEscape;

  public Configuration() {
    encoding = "UTF-8";
    resourceLocater = new FileResourceLocater(this, ".");
    tagRegistry = new TagRegistry(this);
    autoEscape = true;
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

  /** @return Whether or not variables are automatically HTML-escaped. True by default. */
  public boolean getAutoEscape() {
    return autoEscape;
  }

  /**
   * Sets whether or not you want to automatically escape all variable output.
   *
   * <p>By default, all variables are HTML-escaped. You can explicitly mark output as "safe" for output by passing it
   * through html.safe(), as in:
   *
   * <pre><code>{{ html.safe("Some &lt;b&gt;HTML&lt;/b&gt; here") }}</code></pre>
   *
   * Without the call to <code>html.safe</code>, the above would have output
   * "Some &amp;lt;b&amp;gt;HTML&amp;lt;/b&gt; here".
   *
   * @param value If true, output will be automatically HTML-escaped. If false, it would be as if all output is wrapped
   *              in <code>html.safe()</code> by default.
   */
  public void setAutoEscape(boolean value) {
    this.autoEscape = value;
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
