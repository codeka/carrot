package au.com.codeka.carrot.tree;

import au.com.codeka.carrot.lib.Scope;

import java.io.IOException;
import java.io.Writer;

/**
 * Special node that represents the root of the syntax tree.
 */
public class RootNode extends Node {
  public RootNode() {
    super(true /* isBlockNode */);
  }

  @Override
  public void render(Writer writer, Scope scope) throws IOException {
    renderChildren(writer, scope);
  }
}
