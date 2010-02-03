package net.asfun.jangod.tree;

import java.util.Iterator;

public class TreeIterator implements Iterator<Node>{

	Node root;
	Node cursor;
	
	public TreeIterator(Node rootNode) {
		if ( rootNode == null ) {
			root = cursor = null;
		} else {
			root = rootNode;
			cursor = rootNode.children.getFirst();
		}
	}
	
	@Override
	public boolean hasNext() {
		return cursor != null;
	}

	@Override
	public Node next() {
		Node res = cursor;
		if ( cursor != null ) {
			cursor = cursor.treeNext();
		}
		return res;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	
}
