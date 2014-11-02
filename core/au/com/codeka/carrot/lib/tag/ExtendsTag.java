package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tree.NodeList;
import au.com.codeka.carrot.util.HelperStringTokenizer;
import au.com.codeka.carrot.util.ListOrderedMap;

/**
 * {% extends "base.html" %} {% extends var_fileName %}
 */
public class ExtendsTag implements Tag {

  final String TAGNAME = "extends";

  @Override
  public void interpreter(NodeList carries, String helpers, CarrotInterpreter interpreter,
      Writer writer) throws CarrotException, IOException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new InterpretException("Tag 'extends' expects 1 helper >>> " + helper.length);
    }

    ResourceName resourceName = interpreter.findResource(helper[0], true);

    ListOrderedMap blockList = new ListOrderedMap();
    interpreter.assignRuntimeScope(CarrotInterpreter.BLOCK_LIST, blockList, 1);
    CarrotInterpreter parent = interpreter.clone();
    interpreter.assignRuntimeScope(CarrotInterpreter.CHILD_FLAG, true, 1);
    parent.assignRuntimeScope(CarrotInterpreter.PARENT_FLAG, true, 1);
    StringWriter child = new StringWriter();
    parent.render(resourceName, child);
    interpreter.assignRuntimeScope(CarrotInterpreter.SEMI_RENDER, child.toString(), 1);
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
