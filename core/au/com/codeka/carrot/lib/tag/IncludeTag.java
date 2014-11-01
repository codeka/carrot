package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.HelperStringTokenizer;

/**
 * {% include 'sidebar.html' %} {% include var_fileName %}
 * 
 * @author anysome
 *
 */
public class IncludeTag implements Tag {

  final String TAGNAME = "include";

  @Override
  public void interpreter(NodeList carries, String helpers, CarrotInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new InterpretException("Tag 'include' expects 1 helper >>> " + helper.length);
    }
    String templateFile = interpreter.resolveString(helper[0]);
    String resourceName = interpreter.getApplication().getConfiguration().getResourceLocater()
        .findResource(interpreter.getWorkspace(), templateFile);
    Node node = interpreter.getApplication().getParseResult(resourceName);
    CarrotInterpreter child = interpreter.clone();
    child.assignRuntimeScope(CarrotInterpreter.INSERT_FLAG, true, 1);
    child.render(node, writer);
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
