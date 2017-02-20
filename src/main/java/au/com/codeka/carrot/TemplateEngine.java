package au.com.codeka.carrot;

import au.com.codeka.carrot.lib.Scope;
import au.com.codeka.carrot.parse.Tokenizer;
import au.com.codeka.carrot.resource.ResourceLocater;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TreeParser;

import javax.annotation.Nullable;
import java.io.IOException;
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
  private final ParseCache parseCache;
  private final TreeParser treeParser;

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
    this.parseCache = new ParseCache(config);
    this.treeParser = new TreeParser(config);
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
   *                     {@link ResourceLocater}.
   * @param bindings A mapping of string to variables that make up the bindings for this template.
   *
   * @throws CarrotException Thrown if any errors occur.
   */
  public void process(
      Writer writer,
      String templateFile,
      @Nullable Map<String, Object> bindings) throws CarrotException {
    ResourceName resourceName = config.getResourceLocater().findResource(templateFile);
    Node node = parseCache.getNode(resourceName);
    if (node == null) {
      node = treeParser.parse(new Tokenizer(config.getResourceLocater().getReader(resourceName)));
      parseCache.addNode(resourceName, node);
    }

    Scope scope = new Scope(globalBindings);
    if (bindings != null) {
      scope.push(bindings);
    }
    try {
      node.render(writer, scope);
    } catch (IOException e) {
      throw new CarrotException(e);
    }
  }

  /**
   * Process the template with the given filename, and returns the result as a string.
   *
   * @param templateFile The name of the template file, which will be resolved by our configured
   *                     {@link ResourceLocater}.
   * @param bindings A mapping of string to variables that make up the bindings for this template.
   *
   * @throws CarrotException Thrown if any errors occur.
   */
  public String process(String templateFile, @Nullable Map<String, Object> bindings) throws CarrotException {
    StringWriter writer = new StringWriter();
    process(writer, templateFile, bindings);
    return writer.getBuffer().toString();
  }
}
