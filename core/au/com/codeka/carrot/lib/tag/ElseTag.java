package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.NodeList;

/**
 * {% else %}
 * 
 * @author anysome
 *
 */
public class ElseTag implements Tag {

  static final String ELSE = "else";

  @Override
  public void interpreter(NodeList carries, String helpers, JangodInterpreter interpreter,
      Writer writer) throws InterpretException, IOException {
  }

  @Override
  public String getEndTagName() {
    return null;
  }

  @Override
  public String getName() {
    return ELSE;
  }

}
