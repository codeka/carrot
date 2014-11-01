package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.NodeList;

/**
 * {% ifchange var %}
 * 
 * @author anysome
 *
 */
public class IfchangedTag implements Tag {

  final String LASTKEY = "'IF\"CHG";
  final String TAGNAME = "ifchanged";
  final String ENDTAGNAME = "endif";

  @Override
  public void interpreter(NodeList carries, String helpers, CarrotInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    if (helpers.length() == 0) {
      throw new InterpretException("Tag 'ifchanged' expects 1 helper >>> 0");
    }
    boolean isChanged = true;
    String var = helpers;
    Object older = interpreter.fetchRuntimeScope(LASTKEY + var);
    Object test = interpreter.retraceVariable(var);
    if (older == null) {
      if (test == null) {
        isChanged = false;
      }
    } else if (older.equals(test)) {
      isChanged = false;
    }
    interpreter.assignRuntimeScope(LASTKEY + var, test);
    if (isChanged) {
      for (Node node : carries) {
        node.render(interpreter, writer);
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
