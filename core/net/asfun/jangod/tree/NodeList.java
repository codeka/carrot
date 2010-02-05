/**********************************************************************
Copyright (c) 2010 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.jangod.tree;

import java.util.Iterator;

public class NodeList implements Iterable<Node>, Cloneable{

	Node head = null;
	Node tail = null;
	transient int size = 0;
	
	
	/**
	 * trusty call by Node
	 * @param node
	 */
	void add(Node node) {
		if ( head == null ) {
			head = tail = node;
		} else {
			tail.successor = node;
			node.predecessor = tail;
			tail = node;
		}
		size++;
	}
	
	boolean append(Node add) {
		if ( add == null ) {
			return false;
		}
		if ( head == null ) {
			if ( add.parent == null ) {
				return false;
			}
			add.predecessor = add.successor = null;
			head = tail = add;
		} else {
			tail.successor = add;
			add.predecessor = tail;
			add.parent = tail.parent;
			add.successor = null;
			tail = add;
		}
		size++;
		return true;
	}
	
	boolean forend(Node add) {
		if ( add == null ) {
			return false;
		}
		if (head == null ) {
			if ( add.parent == null ) {
				return false;
			}
			add.predecessor = add.successor = null;
			head = tail = add;
		} else {
			head.predecessor = add;
			add.successor = head;
			add.parent = head.parent;
			add.predecessor = null;
			head = add;
		}
		size++;
		return true;
	}
	
	boolean postend(Node to, Node add) {
		if ( to == null || add == null || head == null
				 || to.parent != head.parent || add.parent == to.parent ) {
			return false;
		}
		add.parent = to.parent;
		add.predecessor = to;
		if ( to == tail ) {
			to.successor = add;
			add.successor = null;
			tail = add;
		} else {
			add.successor = to.successor;
			to.successor.predecessor = add;
			to.successor = add;
		}
		size++;
		return true;
	}
	
	boolean preend(Node to, Node add) {
		if ( to == null || add == null || head == null
				 || to.parent != head.parent || add.parent == to.parent ) {
			return false;
		}
		add.parent = to.parent;
		add.successor = to;
		if ( to == head ) {
			add.predecessor = null;
			to.predecessor = add;
			head = add;
		} else {
			add.predecessor = to.predecessor;
			to.predecessor.successor = add;
			to.predecessor = add;
		}
		size++;
		return true;
	}
	
	boolean jumpAfter(Node to, Node jump) {
		if ( to == null || jump == null || head == null 
				|| to.parent != head.parent || jump.parent != head.parent ) {
			return false;
		}
		if ( jump != head ) {
			jump.predecessor.successor = jump.successor;
		}
		if ( jump != tail ) {
			jump.successor.predecessor = jump.predecessor;
		}
		jump.successor = to.successor;
		jump.predecessor = to;
		if ( to == tail ) {
			tail = jump;
		} else {
			to.successor.predecessor = jump;
		}
		to.successor = jump;
		return true;
	}
	
	boolean jumpBefore(Node to, Node jump) {
		if ( to == null || jump == null || head == null 
				|| to.parent != head.parent || jump.parent != head.parent ) {
			return false;
		}
		if ( jump != head ) {
			jump.predecessor.successor = jump.successor;
		}
		if ( jump != tail ) {
			jump.successor.predecessor = jump.predecessor;
		}
		jump.successor = to;
		jump.predecessor = to.predecessor;
		if ( to == head ) {
			head = jump;
		} else {
			to.predecessor.successor = jump;
		}
		to.predecessor = jump;
		return true;
	}

	boolean alternate(Node e, Node n) {
		if ( e == null || n == null || head == null ||
				e.parent != head.parent || n.parent != head.parent ) {
			return false;
		}
		Node pre = e.predecessor;
		Node post = e.successor;
		e.predecessor = n.predecessor;
		if ( n == head ) {
			head = e;
		} else {
			n.predecessor.successor = e;
		}
		e.successor = n.successor;
		if ( n == tail ) {
			tail = e;
		} else {
			n.successor.predecessor = e;
		}
		n.predecessor = pre;
		if ( pre != null ) {
			pre.successor = n;
		} else {
			head = n;
		}
		n.successor = post;
		if ( post != null ) {
			post.predecessor = n;
		} else {
			tail = n;
		}
		return true;
	}
	
	boolean replace(Node to, NodeList with) {
		if ( to == null || with.size == 0 || head == null ||
				to.parent != head.parent || with.head.parent == head.parent ) {
			return false;
		}
		with.head.predecessor = to.predecessor;
		if ( to == head ) {
			head = with.head;
		} else {
			to.predecessor.successor = with.head;
		}
		with.tail.successor = to.successor;
		if ( to == tail ) {
			tail = with.tail;
		} else {
			to.successor.predecessor = with.tail;
		}
		for(Node sibling : with) {
			sibling.parent = to.parent;
		}
		size = size - 1 + with.size;
		return true;
	}
	
	boolean replace(Node to, Node with) {
		if ( to == null || with == null || head == null ||
				to.parent != head.parent || with.parent == head.parent ) {
			return false;
		}
		with.predecessor = to.predecessor;
		if ( to == head ) {
			head = with;
		} else {
			to.predecessor.successor = with;
		}
		with.successor = to.successor;
		if ( to == tail ) {
			tail = with;
		} else {
			to.successor.predecessor = with;
		}
		with.parent = to.parent;
		return true;
	}
	
	boolean remove(Node e) {
		if ( e == null || head == null || 
				e.parent != head.parent ) {
			return false;
		}
		if ( e == head ) {
			head = head.successor;
			head.predecessor = null;
		} 
		else if ( e == tail ) {
			tail = tail.predecessor;
			tail.successor = null;
		}
		else {
			e.predecessor.successor = e.successor;
			e.successor.predecessor = e.predecessor;
		}
		size--;
		return true;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<Node> iterator() {
		return new NodeItr();
	}

	public int size() {
		return size;
	}

	public Node getFirst() {
		return head;
	}

	public Node getLast() {
		return tail;
	}
	
	@Override
	public NodeList clone() {
		NodeList clone = new NodeList();
		for(Node node : this) {
			node.parent = head.parent;
			clone.add(node.clone());
		}
		return clone;
	}
	
	NodeList clone(Node parent) {
		NodeList clone = new NodeList();
		for(Node node : this) {
			Node temp = node.clone();
			temp.parent = parent;
			clone.add(temp);
		}
		return clone;
	}
	
	
	private class NodeItr implements Iterator<Node> {
		
		Node cursor;
		
		NodeItr() {
			cursor = head;
		}

		@Override
		public boolean hasNext() {
			return cursor != null;
		}

		@Override
		public Node next() {
			Node temp = cursor;
			if ( cursor != tail ) {
				cursor = cursor.successor;
			} else {
				cursor = null;
			}
			return temp;
		}

		@Override
		public void remove() {
			NodeList.this.remove(cursor);
		}
		
	}

}
