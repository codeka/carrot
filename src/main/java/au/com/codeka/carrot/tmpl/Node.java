package au.com.codeka.carrot.tmpl;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.resource.ResourcePointer;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for nodes in the abstract syntax tree.
 */
public abstract class Node {
  @Nullable private final List<Node> children;
  private ResourcePointer ptr;

  protected Node(ResourcePointer ptr, boolean isBlockNode) {
    this.ptr = ptr;

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

  public ResourcePointer getPointer() {
    return ptr;
  }

  /** Render this node to the given {@link Writer}. */
  public abstract void render(CarrotEngine engine, Writer writer, Scope scope) throws CarrotException, IOException;

  /** Render all of this {@link Node}'s children to the given {@link Writer}. */
  public void renderChildren(CarrotEngine engine, Writer writer, Scope scope) throws CarrotException, IOException {
    if (children == null) {
      throw new IllegalStateException("Cannot call renderChildren on non-block node.");
    }

    for (Node child : children) {
      try {
        child.render(engine, writer, scope);
      } catch (Exception e) {
        if (e instanceof CarrotException) {
          if (((CarrotException) e).getPointer() == null) {
            throw new CarrotException(e, child.getPointer());
          }
          throw e;
        }
        throw new CarrotException(e, child.getPointer());
      }
    }
  }
}
