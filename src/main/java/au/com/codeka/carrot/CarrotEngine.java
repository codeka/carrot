package au.com.codeka.carrot;

import au.com.codeka.carrot.bindings.SingletonBindings;
import au.com.codeka.carrot.helpers.HtmlHelper;
import au.com.codeka.carrot.resource.ResourceLocater;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tmpl.Node;
import au.com.codeka.carrot.tmpl.TemplateParser;
import au.com.codeka.carrot.tmpl.parse.Tokenizer;
import au.com.codeka.carrot.util.LineReader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * {@link CarrotEngine} is the root of the carrot system. You create an instance of this, make it global or static,
 * load templates and process them from here.
 */
public class CarrotEngine {
  private final Configuration config;
  private final Bindings globalBindings;
  private final ParseCache parseCache;
  private final TemplateParser templateParser;

  /**
   * Constructs a new {@link CarrotEngine} with a default {@link Configuration}.
   *
   * <p>The configuration is mutable, so you can modify once this class has been created.
   */
  public CarrotEngine() {
    this(new Configuration());
  }

  /**
   * Constructs a new {@link CarrotEngine} with the given {@link Configuration}.
   *
   * <p>The configuration is mutable, so you can modify once this class has been created.
   *
   *  @param config The {@link Configuration} to construct this engine with.
   */
  public CarrotEngine(Configuration config) {
    this.config = config;
    this.globalBindings = new SingletonBindings("html", new HtmlHelper());
    this.parseCache = new ParseCache(config);
    this.templateParser = new TemplateParser(config);
  }

  /**
   * @return The {@link Configuration}. The configuration is mutable, so you are able to modify settings on the value
   * returned here and they will take effect on the current {@link CarrotEngine}.
   */
  public Configuration getConfig() {
    return config;
  }

  /**
   * @return A map of the global variables. These bindings will be accessible in all templates processed by this
   * {@link CarrotEngine}.
   */
  public Bindings globalBindings() {
    return globalBindings;
  }

  /**
   * Process the template with the given filename, writing the results to the given {@link Writer}.
   *
   * @param writer A {@link Writer} to write the results of processing the given template to.
   * @param resourceName The {@link ResourceName} of the template file, which will be located by our configured
   *                     {@link ResourceLocater}.
   * @param scope The {@link Scope} we're rendering into.
   *
   * @throws CarrotException Thrown if any errors occur.
   */
  public void process(
      Writer writer,
      ResourceName resourceName,
      Scope scope) throws CarrotException {
    Node node = parseCache.getNode(resourceName);
    if (node == null) {
      LineReader lineReader = new LineReader(resourceName, config.getResourceLocater().getReader(resourceName));
      node = templateParser.parse(new Tokenizer(lineReader));
      parseCache.addNode(resourceName, node);
    }

    try {
      node.render(this, writer, scope);
    } catch (IOException e) {
      throw new CarrotException(e, node.getPointer());
    }
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
      @Nullable Bindings bindings) throws CarrotException {
    ResourceName resourceName = config.getResourceLocater().findResource(templateFile);

    Scope scope = new Scope(globalBindings);
    if (bindings != null) {
      scope.push(bindings);
    }

    process(writer, resourceName, scope);
  }

  /**
   * Process the template with the given filename, and returns the result as a string.
   *
   * @param templateFile The name of the template file, which will be resolved by our configured
   *                     {@link ResourceLocater}.
   * @param bindings A mapping of string to variables that make up the bindings for this template.
   * @return The processed template, as a string.
   *
   * @throws CarrotException Thrown if any errors occur.
   */
  public String process(String templateFile, @Nullable Bindings bindings) throws CarrotException {
    StringWriter writer = new StringWriter();
    process(writer, templateFile, bindings);
    return writer.getBuffer().toString();
  }
}
