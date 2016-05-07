package au.com.codeka.carrot.lib.macro;

import au.com.codeka.carrot.lib.Macro;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TreeRebuilder;
import au.com.codeka.carrot.util.HelperStringTokenizer;

public class BlockMacro implements Macro {

  final String MACRONAME = "block";
  final String ENDMACRONAME = "endblock";
  final String BLK_NAME_PREFIX = "'BLK\"NAME:";

  @Override
  public String getEndMacroName() {
    return ENDMACRONAME;
  }

  @Override
  public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
      throws ParseException {
    String[] helper = new HelperStringTokenizer(helpers).allTokens();
    if (helper.length != 1) {
      throw new ParseException("Macro 'block' expects 1 helper >>> " + helper.length);
    }
    String blockName = rebuilder.resolveString(helper[0]);
    // if is parent
    if (rebuilder.parent == null) {
      rebuilder.assignNode(BLK_NAME_PREFIX + blockName, current);
    } else {
      Node parentBlock = rebuilder.fetchNode(BLK_NAME_PREFIX + blockName);
      if (parentBlock == null) {
        throw new ParseException("Dosen't define block in extends parent with name >>> "
            + blockName);
      }
      // current.replaceWithChildren(parentBlock);
      rebuilder.nodeReplace(parentBlock, current.children());
    }
  }

  @Override
  public String getName() {
    return MACRONAME;
  }

}
