package au.com.codeka.carrot.lib.macro;

import java.io.IOException;

import au.com.codeka.carrot.lib.Macro;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TokenParser;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TreeParser;
import au.com.codeka.carrot.tree.TreeRebuilder;
import au.com.codeka.carrot.util.HelperStringTokenizer;

public class IncludeMacro implements Macro {

  final String MACRONAME = "include";

  @Override
  public String getEndMacroName() {
    return null;
  }

  @Override
  public void refactor(Node current, String helpers, TreeRebuilder rebuilder) throws ParseException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new ParseException("Macro 'include' expects 1 helper >>> " + helper.length);
    }
    String templateFile = rebuilder.resolveString(helper[0]);
    try {
      String fullName = rebuilder.getConfiguration().getResourceLocater().getFullName(
          templateFile, rebuilder.getWorkspace(),
          rebuilder.getConfiguration().getWorkspace());
      // TODO STOP LOOP INCLUDE
      Node includeRoot = new TreeParser(current.application()).parse(
          new TokenParser(rebuilder.getConfiguration().getResourceLocater().getString(
              fullName, rebuilder.getConfiguration().getEncoding())));

      rebuilder.nodeReplace(current, includeRoot.children());
    } catch (IOException e) {
      throw new ParseException(e.getMessage());
    }
  }

  @Override
  public String getName() {
    return MACRONAME;
  }

}
