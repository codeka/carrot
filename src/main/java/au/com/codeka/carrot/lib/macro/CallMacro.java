package au.com.codeka.carrot.lib.macro;

import au.com.codeka.carrot.lib.Macro;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TagToken;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TagNode;
import au.com.codeka.carrot.tree.TreeRebuilder;

public class CallMacro implements Macro {

  final String MACRONAME = "call";

  @Override
  public String getEndMacroName() {
    return null;
  }

  @Override
  public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
      throws ParseException {
    // helpers like name arg2=val2,arg3=var3
    String name = "";// TODO get from helpers;
    Node defineNode = rebuilder.fetchNode(MacroMacro.MACRO_NAME_PREFIX + name);
    if (defineNode == null) {
      throw new ParseException("Call a macro didn't define yet >>> " + name);
    }
    String[] args = new String[] {};// TODO resolve from macro's helpers
    String[] vals = new String[] {};// TODO resolve from helpers and macro's
                                    // helpers
    for (int i = 0; i < args.length; i++) {
      TagNode tn = new TagNode(current.application(),
          new TagToken("{%set " + args[i] + " " + vals[i] + " just %}"));
      rebuilder.nodeInsertBefore(current, tn);
    }
    rebuilder.nodeReplace(current, defineNode.clone().children());
  }

  @Override
  public String getName() {
    return MACRONAME;
  }

}
