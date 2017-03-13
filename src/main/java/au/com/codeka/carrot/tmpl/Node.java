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

  /**
   * Add the given {@link Node} to our list of children. Will only be called if {@link #isBlockNode()} return true.
   *
   * @param child The child {@link Node} to add.
   * @throws IllegalStateException if {@link #isBlockNode()} returns false.
   */
  public void add(Node child) {
    if (children == null) {
      throw new IllegalStateException("Cannot add children to non-block nodes");
    }
    children.add(child);
  }

  /** @return The children of this node, or null if this is not a block node. */
  @Nullable
  public List<Node> getChildren() {
    return children;
  }

  /** @return True if this a block node (i.e. whether it should have children) or not. */
  public boolean isBlockNode() {
    return children != null;
  }

  /**
   * @return The {@link ResourcePointer} that point to this {@link Node}'s position in the resource. Useful for logging
   *    errors and whatnot.
   */
  public ResourcePointer getPointer() {
    return ptr;
  }

  /**
   * Render this node to the given {@link Writer}.
   *
   * @param engine The {@link CarrotEngine} we're running inside of.
   * @param writer The {@link Writer} to write this node to.
   * @param scope The current {@link Scope}, containing all our variables.
   * @throws CarrotException if there's an error parsing or rendering the node.
   * @throws IOException if there's an error writing to the {@link Writer}.
   */
  public abstract void render(CarrotEngine engine, Writer writer, Scope scope) throws CarrotException, IOException;

  /**
   * Render all of this {@link Node}'s children to the given {@link Writer}.
   *
   * @param engine The {@link CarrotEngine} we're running inside of.
   * @param writer The {@link Writer} to write this node to.
   * @param scope The current {@link Scope}, containing all our variables.
   * @throws CarrotException if there's an error parsing or rendering the node.
   * @throws IOException if there's an error writing to the {@link Writer}.
   */
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
