package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.HelperStringTokenizer;

/**
 * {% cycle a,b,c %} {% cycle a,'b',c as d %} {% cycle d %}
 * 
 * @author anysome
 *
 */
public class CycleTag implements Tag {

  final String LOOP_INDEX = "loop.index";
  final String TAGNAME = "cycle";

  @Override
  public void interpreter(NodeList carries, String helpers, JangodInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    String[] values;
    String var = null;
    HelperStringTokenizer tk = new HelperStringTokenizer(helpers);
    // TODO tokenize in one time
    String[] helper = tk.allTokens();
    if (helper.length == 1) {
      HelperStringTokenizer items = new HelperStringTokenizer(helper[0]);
      items.splitComma(true);
      values = items.allTokens();
      Integer forindex = (Integer) interpreter.retraceVariable(LOOP_INDEX);
      if (forindex == null) {
        forindex = 0;
      }
      if (values.length == 1) {
        var = values[0];
        values = (String[]) interpreter.retraceVariable(var);
        if (values == null) {
          writer.write(interpreter.resolveString(var));
        }
      } else {
        for (int i = 0; i < values.length; i++) {
          values[i] = interpreter.resolveString(values[i]);
        }
      }
      writer.write(values[forindex % values.length]);
    } else if (helper.length == 3) {
      HelperStringTokenizer items = new HelperStringTokenizer(helper[0]);
      items.splitComma(true);
      values = items.allTokens();
      for (int i = 0; i < values.length; i++) {
        values[i] = interpreter.resolveString(values[i]);
      }
      var = helper[2];
      interpreter.assignRuntimeScope(var, values);
    } else {
      throw new InterpretException("Tag 'cycle' expects 1 or 3 helper(s) >>> " + helper.length);
    }
  }

  @Override
  public String getEndTagName() {
    return null;
  }

  @Override
  public String getName() {
    return TAGNAME;
  }

}
