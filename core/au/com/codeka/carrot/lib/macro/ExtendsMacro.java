package au.com.codeka.carrot.lib.macro;

import java.io.IOException;

import au.com.codeka.carrot.lib.Macro;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TokenParser;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TreeParser;
import au.com.codeka.carrot.tree.TreeRebuilder;
import au.com.codeka.carrot.util.HelperStringTokenizer;

public class ExtendsMacro implements Macro {

  final String MACRONAME = "extends";

  @Override
  public String getEndMacroName() {
    return null;
  }

  @Override
  public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
      throws ParseException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new ParseException("Macro 'extends' expects 1 helper >>> " + helper.length);
    }
    String templateFile = rebuilder.resolveString(helper[0]);
    try {
      ResourceName resourceName = current.application().getConfiguration().getResourceLocater()
          .findResource(rebuilder.getWorkspace(), templateFile);
      // TODO STOP LOOP EXTENDS
      Node extendsRoot = new TreeParser(current.application()).parse(new TokenParser(
          current.application().getConfiguration().getResourceLocater().getString(resourceName)));
      extendsRoot = rebuilder.derive().refactor(extendsRoot);

      rebuilder.parent = extendsRoot;
      rebuilder.nodeRemove(current);
    } catch (IOException e) {
      throw new ParseException(e.getMessage());
    }
  }

  @Override
  public String getName() {
    return MACRONAME;
  }

}
