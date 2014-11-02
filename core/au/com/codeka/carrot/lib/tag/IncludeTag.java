package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.HelperStringTokenizer;

/**
 * {% include 'sidebar.html' %} {% include var_fileName %}
 */
public class IncludeTag implements Tag {

  final String TAGNAME = "include";

  @Override
  public void interpreter(NodeList carries, String helpers, CarrotInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new InterpretException("<% include %> expects exactly 1 parameter, got: "
          + helper.length);
    }

    ResourceName resourceName = interpreter.findResource(helper[0], true);

    CarrotInterpreter child = interpreter.clone();
    child.assignRuntimeScope(CarrotInterpreter.INSERT_FLAG, true, 1);
    child.render(resourceName, writer);
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
