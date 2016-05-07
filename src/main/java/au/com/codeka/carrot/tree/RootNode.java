package au.com.codeka.carrot.tree;

import java.io.Writer;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;

public class RootNode extends Node {

  private static final long serialVersionUID = 97675838726004658L;
  static final String TREE_ROOT_END = "anysome";

  RootNode(Application app) {
    super(app);
  }

  @Override
  public void render(CarrotInterpreter interpreter, Writer writer) throws InterpretException {
    throw new UnsupportedOperationException("Please render RootNode by interpreter");
  }

  @Override
  public String getName() {
    return TREE_ROOT_END;
  }

  @Override
  public Node clone() {
    Node clone = new RootNode(app);
    clone.children = this.children.clone(clone);
    return clone;
  }
}
