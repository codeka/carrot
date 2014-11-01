package au.com.codeka.carrot.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.base.Context;
import au.com.codeka.carrot.interpret.CarrotInterpreter;

/**
 * Processor for processing a template. Can only be used once and must be
 * re-created each time.
 */
public class Processor {

  protected Context context;
  protected Application application;
  CarrotInterpreter interpreter;

  private boolean used;

  public Processor(Application application) {
    this.application = application;
    context = new Context(application);
    interpreter = new CarrotInterpreter(context);
  }

  public Configuration getConfiguration() {
    return context.getConfiguration();
  }

  public String render(String templateFile, Map<String, Object> bindings) throws CarrotException {
    return render(templateFile, bindings, context.getConfiguration().getEncoding());
  }

  public String render(String templateFile, Map<String, Object> bindings, String encoding)
      throws CarrotException {
    StringWriter writer = new StringWriter();
    render(templateFile, bindings, writer, encoding);
    return writer.getBuffer().toString();
  }

  public void render(String templateFile, Map<String, Object> bindings, Writer writer)
      throws CarrotException {
    render(templateFile, bindings, writer, context.getConfiguration().getEncoding());
  }

  public void render(String templateFile, Map<String, Object> bindings, Writer writer,
      String encoding) throws CarrotException {
    if (used) {
      throw new IllegalStateException("Cannot use Processor more than once.");
    }
    used = true;

    context.initBindings(bindings, Context.SCOPE_SESSION);
    try {
      String fullName = application.getConfiguration().getResourceLocater().getFullName(
        templateFile, application.getConfiguration().getWorkspace());
      interpreter.setFile(fullName);
      interpreter.init();
      interpreter.render(application.getParseResult(fullName, encoding), writer);
    } catch (IOException e) {
      throw new CarrotException(e);
    }
  }
}
