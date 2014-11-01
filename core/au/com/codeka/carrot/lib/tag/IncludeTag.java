package au.com.codeka.carrot.lib.tag;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
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
  public void interpreter(NodeList carries, String helpers, JangodInterpreter interpreter,
      Writer writer) throws InterpretException, IOException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new InterpretException("Tag 'include' expects 1 helper >>> " + helper.length);
    }
    String templateFile = interpreter.resolveString(helper[0]);
    String fullName = interpreter.getApplication().getConfiguration().getResourceLocater()
        .getFullName(templateFile, interpreter.getWorkspace(),
            interpreter.getConfiguration().getWorkspace());
    Node node = interpreter.getApplication().getParseResult(
        fullName, interpreter.getConfiguration().getEncoding());
    JangodInterpreter child = interpreter.clone();
    child.assignRuntimeScope(JangodInterpreter.INSERT_FLAG, true, 1);
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
