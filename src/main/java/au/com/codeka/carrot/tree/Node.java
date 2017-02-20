package au.com.codeka.carrot.tree;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for nodes in the abstract syntax tree.
 */
public class Node {
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
}
