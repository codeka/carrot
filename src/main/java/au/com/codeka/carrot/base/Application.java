package au.com.codeka.carrot.base;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.ParseResultManager;

public class Application {

  Map<String, Object> globalBindings = new Hashtable<String, Object>(5);
  Configuration config;
  ParseResultManager parseResultManager;
  boolean isMacroOn = true;

  public Application() {
    this(null);
  }

  public Application(Configuration config) {
    if (config == null) {
      config = new Configuration();
    }
    this.config = config;
    this.parseResultManager = new ParseResultManager(this);
  }

  public Map<String, Object> getGlobalBindings() {
    return globalBindings;
  }

  public void setGlobalBindings(Map<String, Object> globalBindings) {
    this.globalBindings = globalBindings;
  }

  public Configuration getConfiguration() {
    return config;
  }

  public Node getParseResult(ResourceName resourceName) throws CarrotException, IOException {
    return parseResultManager.getParseResult(resourceName);
  }
}
