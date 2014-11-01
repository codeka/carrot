package au.com.codeka.carrot.tree;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.JangodInterpreter;

public abstract class Node implements Serializable, Cloneable {

  private static final long serialVersionUID = 7323842986596895498L;

  Application app;
  int level = 0;
  Node parent = null;
  Node predecessor = null;
  Node successor = null;
  NodeList children = new NodeList();

  protected Node(Application app) {
    this.app = app;
  }

  public Node parent() {
    return parent;
  }

  public Node predecessor() {
    return predecessor;
  }

  public Node successor() {
    return successor;
  }

  public Application application() {
    return this.app;
  }

  boolean replaceWithChildren(Node tobeReplace) {
    if (tobeReplace.parent == null) {
      return false;
    }
    return tobeReplace.parent.children.replace(tobeReplace, children);
  }

  public NodeList children() {
    return children;
  }

  @Override
  public abstract Node clone();

  boolean remove() {
    if (parent != null) {
      return parent.children.remove(this);
    }
    return false;
  }

  /**
   * trusty call by TreeParser
   * 
   * @param node
   */
  void add(Node node) {
    node.level = level + 1;
    node.parent = this;
    children.add(node);
  }

  Node treeNext() {
    if (children.size > 0) {
      return children.head;
    } else {
      return recursiveNext();
    }
  }

  Node recursiveNext() {
    if (successor != null) {
      return successor;
    } else {
      if (parent != null) {
        return parent.recursiveNext();
      } else {
        return null;
      }
    }
  }

  boolean addChild(Node node) {
    node.parent = this;
    return children.append(node);
  }

  boolean followMe(Node node) {
    if (parent != null) {
      return parent.children.postend(this, node);
    }
    return false;
  }

  boolean leadMe(Node node) {
    if (parent != null) {
      return parent.children.preend(this, node);
    }
    return false;
  }

  boolean exchange(Node node) {
    if (parent == null || node == null || node.parent == null) {
      return false;
    }
    if (parent == node.parent) {
      parent.children.alternate(this, node);
    } else {
      Node tempPar = node.parent;
      Node tempPre = node.predecessor;
      Node tempSuc = node.successor;
      node.parent = parent;
      node.predecessor = predecessor;
      node.successor = successor;
      if (predecessor != null) {
        predecessor.successor = node;
      } else {
        parent.children.head = node;
      }
      if (successor != null) {
        successor.predecessor = node;
      } else {
        parent.children.tail = node;
      }
      parent = tempPar;
      predecessor = tempPre;
      successor = tempSuc;
      if (predecessor != null) {
        predecessor.successor = this;
      } else {
        parent.children.head = this;
      }
      if (successor != null) {
        successor.predecessor = this;
      } else {
        parent.children.tail = this;
      }
    }
    return true;
  }

  void computeLevel(int baseLevel) {
    // for(int i=0;i<baseLevel;i++)
    // System.out.print("  ");
    // System.out.println("\\" + baseLevel);

    level = baseLevel;
    for (Node child : children) {
      child.computeLevel(level + 1);
    }
  }

  public abstract void render(JangodInterpreter interpreter, Writer writer)
      throws CarrotException, IOException;

  public abstract String getName();

}
