package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.interpret.VariableFilter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.ForLoop;
import au.com.codeka.carrot.util.HelperStringTokenizer;
import au.com.codeka.carrot.util.ObjectIterator;

/**
 * {% for a in b|f1:d,c %}
 * 
 * @author anysome
 *
 */
public class ForTag implements Tag {

  final String LOOP = "loop";
  final String TAGNAME = "for";
  final String ENDTAGNAME = "endfor";

  @Override
  public void interpreter(NodeList carries, String helpers, CarrotInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 3) {
      throw new InterpretException("Tag 'for' expects 3 helpers >>> " + helper.length);
    }
    String item = helper[0];
    Object collection = VariableFilter.compute(helper[2], interpreter);
    ForLoop<Object> loop = ObjectIterator.getLoop(collection);

    int level = interpreter.getLevel() + 1;
    interpreter.assignRuntimeScope(LOOP, loop, level);
    while (loop.hasNext()) {
      // set item variable
      interpreter.assignRuntimeScope(item, loop.next(), level);
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
