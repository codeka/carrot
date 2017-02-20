package au.com.codeka.carrot;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link TemplateEngine} is the root of the carrot system. You create an instance of this, make it global or static,
 * load templates and process them from here.
 */
public class TemplateEngine {
  private final Configuration config;
  private final Map<String, Object> globalBindings;

  /**
   * Constructs a new {@link TemplateEngine} with a default {@link Configuration}.
   *
   * <p>The configuration is mutable, so you can modify once this class has been created.
   */
  public TemplateEngine() {
    this(new Configuration());
  }

  /**
   * Constructs a new {@link TemplateEngine} with the given {@link Configuration}.
   *
   * <p>The configuration is mutable, so you can modify once this class has been created.
   *
   *  @param config The {@link Configuration} to construct this engine with.
   */
  public TemplateEngine(Configuration config) {
    this.config = config;
    this.globalBindings = new HashMap<>();
  }

  /**
   * Gets the {@link Configuration}. The configuration is mutable, so you are able to modify settings on the value
   * returned here and they will take effect on the current {@link TemplateEngine}.
   */
  public Configuration getConfig() {
    return config;
  }

  /**
   * Get a map of the global variables. These bindings will be accessible in all templates processed by this
   * {@link TemplateEngine}.
   */
  public Map<String, Object> getGlobalBindings() {
    return globalBindings;
  }

  /**
   * Process the template with the given filename, writing the results to the given {@link Writer}.
   *
   * @param writer A {@link Writer} to write the results of processing the given template to.
   * @param templateFile The name of the template file, which will be resolved by our configured
   *                     {@Link ResourceLocator}.
   * @param bindings A mapping of string to variables that make up the bindings for this template.
   *
   * @throws CarrotException Thrown if any errors occur.
   */
  public void process(Writer writer, String templateFile, Map<String, Object> bindings) throws CarrotException {

  }

  /**
   * Process the template with the given filename, and returns the result as a string.
   *
   * @param templateFile The name of the template file, which will be resolved by our configured
   *                     {@Link ResourceLocator}.
   * @param bindings A mapping of string to variables that make up the bindings for this template.
   *
   * @throws CarrotException Thrown if any errors occur.
   */
  public String process(String templateFile, Map<String, Object> bindings) throws CarrotException {
    StringWriter writer = new StringWriter();
    process(writer, templateFile, bindings);
    return writer.getBuffer().toString();
  }
}
