package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.interpret.VariableFilter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.ObjectTruthValue;

/**
 * {% if a|f1:b,c|f2 %}
 * 
 * @author anysome
 *
 */
public class IfTag implements Tag {

  final String TAGNAME = "if";
  final String ENDTAGNAME = "endif";

  @Override
  public void interpreter(NodeList carries, String helpers, JangodInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    if (helpers.length() == 0) {
      throw new InterpretException("Tag 'if' expects 1 helper >>> 0");
    }
    Object test = VariableFilter.compute(helpers, interpreter);
    if (ObjectTruthValue.evaluate(test)) {
      for (Node node : carries) {
        if (ElseTag.ELSE.equals(node.getName())) {
          break;
        }
        node.render(interpreter, writer);
      }
    } else {
      boolean inElse = false;
      for (Node node : carries) {
        if (inElse) {
          node.render(interpreter, writer);
        }
        if (ElseTag.ELSE.equals(node.getName())) {
          inElse = true;
        }
      }
    }
  }

  @Override
  public String getEndTagName() {
    return ENDTAGNAME;
  }

  @Override
  public String getName() {
    return TAGNAME;
  }

}
