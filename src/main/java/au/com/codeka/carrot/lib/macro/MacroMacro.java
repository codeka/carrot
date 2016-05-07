package au.com.codeka.carrot.lib.macro;

import au.com.codeka.carrot.lib.Macro;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TreeRebuilder;

public class MacroMacro implements Macro {

  final String MACRONAME = "macro";
  final String ENDMACRONAME = "endmacro";
  static final String MACRO_NAME_PREFIX = "'MACRO\"NAME:";

  @Override
  public String getEndMacroName() {
    return ENDMACRONAME;
  }

  @Override
  public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
      throws ParseException {
    // helpers like name arg1=val1,arg2=val2,arg3,
    String name = "";// TODO resolve from helpers
    // TODO save param form to rebuilder
    rebuilder.assignNode(MACRO_NAME_PREFIX + name, current);
    rebuilder.nodeRemove(current);
  }

  @Override
  public String getName() {
    return MACRONAME;
  }

}
