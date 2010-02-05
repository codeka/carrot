package net.asfun.jangod.tree;

public class NodeAction {

	public static boolean remove(Node node) {
		return node.remove();
	}
	
	public static boolean exchange(Node node1, Node node2) {
		return node1.exchange(node2);
	}
	
	public static boolean replace(Node tobe, Node with) {
		if ( tobe.parent != null ) {
			return tobe.parent.children.replace(tobe, with);
		}
		return false;
	}
	
	public static boolean replace(Node tobe, NodeList with) {
		if ( tobe.parent != null ) {
			return tobe.parent.children.replace(tobe, with);
		}
		return false;
	}
	
	public static boolean insertAfter(Node node, Node toAdd) {
		if ( node.parent != null ) {
			if ( node.parent == toAdd.parent ) {
				return node.parent.children.jumpAfter(node, toAdd);
			} else {
				return node.parent.children.postend(node, toAdd);
			}
		}
		return false;
	}
	
	public static boolean insertBefore(Node node, Node toAdd) {
		if ( node.parent != null ) {
			if ( node.parent == toAdd.parent ) {
				return node.parent.children.jumpBefore(node, toAdd);
			} else {
				return node.parent.children.preend(node, toAdd);
			}
		}
		return false;
	}
	
	public static boolean addChildLast(Node parent, Node toAdd) {
		if ( toAdd.parent == parent ) {
			return parent.children.jumpAfter(parent.children.tail, toAdd);
		} else {
			return parent.addChild(toAdd);
		}
	}
	
	public static boolean addChildFirst(Node parent, Node toAdd) {
		if ( toAdd.parent == parent ) {
			return parent.children.jumpBefore(parent.children.head, toAdd);
		} else {
			return parent.children.forend(toAdd);
		}
	}
}
