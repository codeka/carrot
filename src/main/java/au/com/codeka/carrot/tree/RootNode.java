package au.com.codeka.carrot.tree;

/**
 * Special node that represents the root of the syntax tree.
 */
public class RootNode extends Node {
  public RootNode() {
    super(true /* isBlockNode */);
  }
}
