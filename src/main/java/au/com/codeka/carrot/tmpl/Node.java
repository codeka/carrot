package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.lib.Scope;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for nodes in the abstract syntax tree.
 */
public abstract class Node {
  @Nullable
  private final List<Node> children;

  protected Node(boolean isBlockNode) {
    if (isBlockNode) {
      children = new ArrayList<>();
    } else {
      children = null;
    }
  }

  public void add(Node child) {
    if (children == null) {
      throw new IllegalStateException("Cannot add children to non-block nodes");
    }
    children.add(child);
  }

  /** Gets the children of this node, or null if this is not a block node. */
  @Nullable
  public List<Node> getChildren() {
    return children;
  }

  public boolean isBlockNode() {
    return children != null;
  }

  /** Render this node to the given {@link Writer}. */
  public abstract void render(Configuration config, Writer writer, Scope scope) throws CarrotException, IOException;

  /** Render all of this {@link Node}'s children to the given {@link Writer}. */
  public void renderChildren(Configuration config, Writer writer, Scope scope) throws CarrotException, IOException {
    if (children == null) {
      throw new IllegalStateException("Cannot call renderChildren on non-block node.");
    }

    for (Node child : children) {
      child.render(config, writer, scope);
    }
  }
}
