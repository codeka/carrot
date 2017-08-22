package au.com.codeka.carrot;

import au.com.codeka.carrot.resource.MemoryResourceLocator;
import au.com.codeka.carrot.resource.ResourceLocator;

/**
 * The {@link Configuration} is used to configure various aspects of the carrot engine.
 */
public class Configuration {
  public interface Logger {
    int LEVEL_DEBUG = 1;
    int LEVEL_INFO = 2;
    int LEVEL_WARNING = 3;

    void print(int level, String msg);
  }

  private final String encoding;
  private final ResourceLocator resourceLocator;
  private final TagRegistry tagRegistry;
  private final Logger logger;
  private final boolean autoEscape;

  private Configuration(
      String encoding,
      ResourceLocator.Builder resourceLocatorBuilder,
      TagRegistry.Builder tagRegistryBuilder,
      Logger logger,
      boolean autoEscape) {
    this.encoding = encoding;
    this.resourceLocator = resourceLocatorBuilder.build(this);
    this.tagRegistry = tagRegistryBuilder.build(this);
    this.logger = logger;
    this.autoEscape = autoEscape;
  }

  public String getEncoding() {
    return encoding;
  }

  public ResourceLocator getResourceLocator() {
    return resourceLocator;
  }

  public TagRegistry getTagRegistry() {
    return tagRegistry;
  }

  /** @return Whether or not variables are automatically HTML-escaped. True by default. */
  public boolean getAutoEscape() {
    return autoEscape;
  }

  public Logger getLogger() {
    return logger;
  }

  public static class Builder {
    private String encoding;
    private ResourceLocator.Builder resourceLocatorBuilder;
    private TagRegistry.Builder tagRegistryBuilder;
    private Logger logger;
    private boolean autoEscape;

    public Builder() {
      encoding = "utf-8";
      autoEscape = true;
    }

    public Builder setEncoding(String encoding) {
      this.encoding = encoding;
      return this;
    }

    public Builder setResourceLocator(ResourceLocator.Builder resourceLocatorBuilder) {
      this.resourceLocatorBuilder = resourceLocatorBuilder;
      return this;
    }

    public Builder setTagRegistry(TagRegistry.Builder tagRegistryBuilder) {
      this.tagRegistryBuilder = tagRegistryBuilder;
      return this;
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
     * @return The current {@link Builder}.
     */
    public Builder setAutoEscape(boolean value) {
      this.autoEscape = value;
      return this;
    }

    public Builder setLogger(Logger logger) {
      this.logger = logger;
      return this;
    }

    public Configuration build() {
      return new Configuration(
          encoding,
          resourceLocatorBuilder == null ? new MemoryResourceLocator.Builder() : resourceLocatorBuilder,
          tagRegistryBuilder == null ? new TagRegistry.Builder() : tagRegistryBuilder,
          logger,
          autoEscape);
    }
  }
}
