package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.tree.Node;
import au.com.codeka.carrot.tree.TreeRebuilder;

public interface Macro extends Importable {
  public String getEndMacroName();

  public void refactor(Node current, String helpers, TreeRebuilder rebuilder)
      throws ParseException;
}
