package au.com.codeka.carrot.interpret;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.base.Constants;
import au.com.codeka.carrot.base.Context;
import au.com.codeka.carrot.parse.TokenParser;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TreeParser;
import au.com.codeka.carrot.util.ListOrderedMap;
import au.com.codeka.carrot.util.ListOrderedMap.Item;
import au.com.codeka.carrot.util.Log;
import au.com.codeka.carrot.util.Variable;

public class CarrotInterpreter implements Cloneable {

  public static final String CHILD_FLAG = "'IS\"CHILD";
  public static final String PARENT_FLAG = "'IS\"PARENT";
  public static final String INSERT_FLAG = "'IS\"INSERT";
  public static final String SEMI_RENDER = "'SEMI\"FORMAL";
  public static final String BLOCK_LIST = "'BLK\"LIST";
  public static final String SEMI_BLOCK = "<K2C9OL7B>";

  static final String VAR_DATE = "now";
  static final String VAR_PATH = "workspace";

  private int level = 1;
  private FloorBindings runtime;
  private Context context;
  private Log log;
  private ResourceName workspace;

  public CarrotInterpreter(Context context) {
    this.context = context;
    runtime = new FloorBindings();
    log = new Log(context.getApplication().getConfiguration());
  }

  private CarrotInterpreter() {
  }

  public Configuration getConfiguration() {
    return context.getConfiguration();
  }

  @Override
  public CarrotInterpreter clone() {
    CarrotInterpreter compiler = new CarrotInterpreter();
    compiler.context = context;
    compiler.runtime = runtime.clone();
    compiler.log = log;
    return compiler;
  }

  public void init() {
    runtime = new FloorBindings();
    level = 1;
  }

  /**
   * Finds the {@link ResourceName} of the resource with the given name, relative to our current
   * workspace.
   *
   * @param name The name of the resource to find.
   * @param resolveName {@code true} if the name is a variable that we may need to resolve first.
   * @return A {@link ResourceName} for the resource with the given name.
   * @throws IOException
   */
  public ResourceName findResource(String name, boolean resolveName)
      throws CarrotException, IOException {
    if (resolveName) {
      name = resolveString(name);
    }
    return getApplication().getConfiguration().getResourceLocater()
        .findResource(getWorkspace(), name);
  }

  public void render(TokenParser tokenParser, Writer writer) throws CarrotException, IOException {
    workspace = null;
    render(new TreeParser(context.getApplication()).parse(tokenParser), writer);
  }

  public void render(ResourceName resourceName, Writer writer) throws CarrotException, IOException {
    workspace = resourceName.getParent();
    render(context.getApplication().getParseResult(resourceName), writer);
  }

  private void render(Node root, Writer writer) throws CarrotException, IOException {
    for (Node node : root.children()) {
      node.render(this, writer);
    }
    if (runtime.get(CHILD_FLAG, 1) != null &&
        runtime.get(INSERT_FLAG, 1) == null) {
      StringBuilder sb = new StringBuilder((String) fetchRuntimeScope(SEMI_RENDER, 1));
      // replace the block identify with block content
      ListOrderedMap blockList = (ListOrderedMap) fetchRuntimeScope(BLOCK_LIST, 1);
      Iterator<Item> mi = blockList.iterator();
      int index;
      String replace;
      Item item;
      while (mi.hasNext()) {
        item = mi.next();
        replace = SEMI_BLOCK + item.getKey();
        while ((index = sb.indexOf(replace)) > 0) {
          sb.delete(index, index + replace.length());
          sb.insert(index, item.getValue());
        }
      }
      writer.write(sb.toString());
    }
  }

  /**
   * Look for the given variable name.
   *
   * @param variableName The name of the variable to resolve to a value.
   * @return The variable, if it can be resolved, or null otherwise.
   * @throws CarrotException if there is an exception trying to call user-supplied methods.
   */
  //@Nullable
  public Object retraceVariable(String variableName) throws CarrotException {
    if (variableName == null || variableName.trim().length() == 0) {
      // No variable, just return empty string.
      return "";
    }
    Variable var = new Variable(variableName);
    String varName = var.getName();
    // find from runtime(tree scope) > engine > global
    Object obj = runtime.get(varName, level);
    int lvl = level;
    while (obj == null && lvl > 1) {
      obj = runtime.get(varName, --lvl);
    }
    if (obj == null) {
      obj = context.getAttribute(varName);
    }
    if (obj == null) {
      if (VAR_DATE.equals(variableName)) {
        return new java.util.Date();
      }
      if (VAR_PATH.equals(variableName)) {
        return getWorkspace();
      }
    }
    if (obj != null) {
      obj = var.resolve(obj);
    }
    return obj;
  }

  public String resolveString(String variable) throws CarrotException {
    if (variable == null || variable.trim().length() == 0) {
      throw new InterpretException("Variable name is required.");
    }
    if (variable.startsWith(Constants.STR_DOUBLE_QUOTE) ||
        variable.startsWith(Constants.STR_SINGLE_QUOTE)) {
      return variable.substring(1, variable.length() - 1);
    } else {
      Object val = retraceVariable(variable);
      if (val == null)
        return variable;
      return val.toString();
    }
  }

  public Object resolveObject(String variable) throws CarrotException {
    if (variable == null || variable.trim().length() == 0) {
      throw new InterpretException("Variable name is required.");
    }
    if (variable.startsWith(Constants.STR_DOUBLE_QUOTE) ||
        variable.startsWith(Constants.STR_SINGLE_QUOTE)) {
      return variable.substring(1, variable.length() - 1);
    } else {
      Object val = retraceVariable(variable);
      if (val == null)
        return variable;
      return val;
    }
  }

  /**
   * save variable to runtime tree scope space
   * 
   * @param name
   * @param item
   */
  public void assignRuntimeScope(String name, Object item) {
    runtime.put(name, item, level);
  }

  public void assignRuntimeScope(String name, Object item, int level) {
    runtime.put(name, item, level);
  }

  public Object fetchRuntimeScope(String name, int level) {
    return runtime.get(name, level);
  }

  public Object fetchRuntimeScope(String name) {
    return runtime.get(name, level);
  }

  public void setLevel(int lvl) {
    level = lvl;
  }

  public int getLevel() {
    return level;
  }

  public Context getContext() {
    return context;
  }

  public Application getApplication() {
    return context.getApplication();
  }

  public ResourceName getWorkspace() {
    return workspace;
  }

  public void setCurrentResource(ResourceName resourceName) {
    workspace = resourceName.getParent();
  }
}
