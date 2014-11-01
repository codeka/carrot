package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.interpret.VariableFilter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.HelperStringTokenizer;

/**
 * {% set varName post.id|equal:'12' scope %}
 * 
 * @author anysome
 *
 */
public class SetTag implements Tag {

  final String TAGNAME = "set";
  final String SCOPE_TOP = "top";

  @Override
  public String getName() {
    return TAGNAME;
  }

  @Override
  public void interpreter(NodeList carries, String helpers, CarrotInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length < 2 || helper.length > 3) {
      throw new InterpretException("Tag 'set' expects 2 or 3 helper >>> " + helper.length);
    }
    String scope = SCOPE_TOP;
    if (helper.length == 3) {
      scope = helper[2].toLowerCase();
    }
    Object value = VariableFilter.compute(helper[1], interpreter);
    if (SCOPE_TOP.equals(scope)) {
      interpreter.assignRuntimeScope(helper[0], value, 1);
    } else {
      interpreter.assignRuntimeScope(helper[0], value);
    }
  }

  @Override
  public String getEndTagName() {
    return null;
  }

}
