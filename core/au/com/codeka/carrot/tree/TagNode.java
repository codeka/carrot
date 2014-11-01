package au.com.codeka.carrot.tree;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.lib.TagLibrary;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TagToken;

public class TagNode extends Node {

  private static final long serialVersionUID = 2405693063353887509L;

  private TagToken master;
  String endName = null;

  public TagNode(Application app, TagToken token) throws ParseException {
    super(app);
    master = token;
    Tag tag = TagLibrary.getTag(master.getTagName());
    if (tag == null) {
      throw new ParseException("Can't find tag >>> " + master.getTagName());
    }
    endName = tag.getEndTagName();
  }

  @Override
  public void render(CarrotInterpreter interpreter, Writer writer)
      throws CarrotException, IOException {
    interpreter.setLevel(level);
    Tag tag = TagLibrary.getTag(master.getTagName());
    tag.interpreter(children(), master.getHelpers(), interpreter, writer);
  }

  @Override
  public String toString() {
    return master.toString();
  }

  @Override
  public String getName() {
    return master.getTagName();
  }

  @Override
  public Node clone() {
    try {
      Node clone = new TagNode(app, master);
      clone.children = this.children.clone(clone);
      return clone;
    } catch (ParseException e) {
      throw new InternalError();
    }
  }
}
