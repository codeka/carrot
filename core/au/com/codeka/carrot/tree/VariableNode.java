package au.com.codeka.carrot.tree;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.lib.FilterLibrary;
import au.com.codeka.carrot.parse.EchoToken;
import au.com.codeka.carrot.util.Log;
import au.com.codeka.carrot.util.ObjectValue;

public class VariableNode extends Node {

  private static final long serialVersionUID = 1;
  private EchoToken master;
  private Log log;
  static final String name = "Variable_Node";

  public VariableNode(Application app, EchoToken token) {
    super(app);
    master = token;
    log = new Log(app.getConfiguration());
  }

  @Override
  public void render(CarrotInterpreter interpreter, Writer writer)
      throws CarrotException, IOException {
    interpreter.setLevel(level);
    Object var = interpreter.retraceVariable(master.getVariable());
    // filters
    List<String> filters = master.getFilters();
    if (filters.isEmpty()) {
      writer.write(ObjectValue.printable(var));
      return;
    }
    List<String[]> argss = master.getArgss();
    String[] args;
    Filter filter;
    for (int i = 0; i < filters.size(); i++) {
      filter = FilterLibrary.getFilter(filters.get(i));
      if (filter == null) {
        log.warn("Skipping an unregistered filter: %s", filters.get(i));
        continue;
      }
      args = argss.get(i);
      if (args == null) {
        var = filter.filter(var, interpreter);
      } else {
        var = filter.filter(var, interpreter, args);
      }
    }
    writer.write(ObjectValue.printable(var));
  }

  @Override
  public String toString() {
    return master.toString();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Node clone() {
    Node clone = new VariableNode(app, master);
    clone.children = this.children.clone(clone);
    return clone;
  }
}
