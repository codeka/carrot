package au.com.codeka.carrot.tree;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.base.Constants;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.resource.ResourceName;

public class TreeRebuilder {

  private final ResourceName currResource;
  private final Application application;
  private final Map<String, Node> capture = new HashMap<String, Node>();
  private final Map<String, Object> variables = new HashMap<String, Object>();
  private final LinkedList<Method> actions = new LinkedList<Method>();
  private List<Object[]> msgs = new LinkedList<Object[]>();
  public Node parent = null;

  public TreeRebuilder(Application application) {
    this.application = application;
    this.currResource = null;
  }

  TreeRebuilder(Application application, ResourceName currResource) {
    this.application = application;
    this.currResource = currResource;
  }

  /**
   * share the same capture nodes
   * 
   * @return
   */
  public TreeRebuilder derive() {
    TreeRebuilder tr = new TreeRebuilder(this.application, currResource);
    tr.capture.putAll(this.capture);
    tr.variables.putAll(this.variables);
    tr.parent = this.parent;
    return tr;
  }

  public String resolveString(String string) {
    if (string == null || string.trim().length() == 0) {
      return "";
    }
    if (string.startsWith(Constants.STR_DOUBLE_QUOTE) ||
        string.startsWith(Constants.STR_SINGLE_QUOTE)) {
      return string.substring(1, string.length() - 1);
    }
    return string;
  }

  public ResourceName getWorkspace() {
    if (currResource == null) {
      return null;
    }
    return currResource.getParent();
  }

  public void assignNode(String key, Node node) {
    capture.put(key, node);
  }

  public Node fetchNode(String key) {
    return capture.get(key);
  }

  public void assignVariable(String key, Object value) {
    variables.put(key, value);
  }

  public Object fetchVariable(String key, Object value) {
    return variables.get(key);
  }
/*
  public void setFile(String fullName) {
    this.file = fullName;
  }
*/
  public Configuration getConfiguration() {
    return application.getConfiguration();
  }

  public Application getApplication() {
    return application;
  }

  public Node refactor(Node root) throws ParseException {
    boolean needRelevel = false;
    TreeIterator nit = new TreeIterator(root);
    Node temp = null;
    while (nit.hasNext()) {
      temp = nit.next();
      if (temp instanceof MacroNode) {
        ((MacroNode) temp).refactor(this);
        needRelevel = true;
      }
    }
    // real refactor must run after iterated to make sure iterator correct
    if (!actions.isEmpty()) {
      Iterator<Object[]> it = msgs.iterator();
      try {
        for (Method action : actions) {
          Object[] msg = it.next();
          action.invoke(null, msg);
        }
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
    if (parent != null) {
      root = parent;
    }
    if (needRelevel) {
      root.computeLevel(0);
    }
    return root;
  }

  /**
   * Replace a node with many nodes.<br />
   * It will take place after node tree iterate.
   * 
   * @param tobe
   *          The node tobe replaced
   * @param with
   *          The new nodelist instead of old node
   */
  public void nodeReplace(Node tobe, NodeList with) throws ParseException {
    if (tobe != null) {
      try {
        // keep fresh, don't use node.parent, node.predecessor, node.successor
        // in msg.
        Method action = NodeAction.class.getDeclaredMethod("replace",
            new Class[] { Node.class, NodeList.class });
        Object[] msg = new Object[] { tobe, with };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }

  public void nodeReplace(Node tobe, Node with) throws ParseException {
    if (tobe != null) {
      try {
        Method action = NodeAction.class.getDeclaredMethod("replace",
            new Class[] { Node.class, Node.class });
        Object[] msg = new Object[] { tobe, with };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }

  public void nodeRemove(Node node) throws ParseException {
    if (node != null) {
      try {
        Method action = NodeAction.class.getDeclaredMethod("remove",
            new Class[] { Node.class });
        Object[] msg = new Object[] { node };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }

  public void nodeInsertAfter(Node node, Node toAdd) throws ParseException {
    if (node != null && toAdd != null) {
      try {
        Method action = NodeAction.class.getDeclaredMethod("insertAfter",
            new Class[] { Node.class, Node.class });
        Object[] msg = new Object[] { node, toAdd };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }

  public void nodeInsertBefore(Node node, Node toAdd) throws ParseException {
    if (node != null && toAdd != null) {
      try {
        Method action = NodeAction.class.getDeclaredMethod("insertBefore",
            new Class[] { Node.class, Node.class });
        Object[] msg = new Object[] { node, toAdd };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }

  public void nodeAddChildLast(Node parent, Node toAdd) throws ParseException {
    if (parent != null && toAdd != null) {
      try {
        Method action = NodeAction.class.getDeclaredMethod("addChildLast",
            new Class[] { Node.class, Node.class });
        Object[] msg = new Object[] { parent, toAdd };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }

  public void nodeAddChildFirst(Node parent, Node toAdd) throws ParseException {
    if (parent != null && toAdd != null) {
      try {
        Method action = NodeAction.class.getDeclaredMethod("addChildFirst",
            new Class[] { Node.class, Node.class });
        Object[] msg = new Object[] { parent, toAdd };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }

  public void nodeExchange(Node node1, Node node2) throws ParseException {
    if (node1 != null && node2 != null) {
      try {
        Method action = NodeAction.class.getDeclaredMethod("exchange",
            new Class[] { Node.class, Node.class });
        Object[] msg = new Object[] { node1, node2 };
        actions.add(action);
        msgs.add(msg);
      } catch (Exception e) {
        throw new ParseException(e);
      }
    }
  }
}
