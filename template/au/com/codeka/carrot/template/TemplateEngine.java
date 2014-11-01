package au.com.codeka.carrot.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.Configuration;

public class TemplateEngine {
  Application application;

  public TemplateEngine() {
    application = new Application();
  }

  public TemplateEngine(Application application) {
    this.application = application;
  }

  public void setEngineBindings(Map<String, Object> bindings) {
    if (bindings == null) {
      application.getGlobalBindings().clear();
    } else {
      application.setGlobalBindings(bindings);
    }
  }

  public String process(String templateFile, Map<String, Object> bindings) throws IOException {
    return new Processor(application).render(templateFile, bindings);
  }

  public void process(String templateFile, Map<String, Object> bindings, Writer out)
      throws IOException {
    new Processor(application).render(templateFile, bindings, out);
  }

  public Configuration getConfiguration() {
    return application.getConfiguration();
  }
}
