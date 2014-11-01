package au.com.codeka.carrot.tree;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.parse.FixedToken;

public class TextNode extends Node {

  private static final long serialVersionUID = 8488738480534354216L;
  private FixedToken master;
  static final String name = "Text_Node";

  public TextNode(Application app, FixedToken token) {
    super(app);
    master = token;
  }

  @Override
  public void render(JangodInterpreter interpreter, Writer writer)
      throws InterpretException, IOException {
    master.output(writer);
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
    Node clone = new TextNode(app, master);
    clone.children = this.children.clone(clone);
    return clone;
  }
}
