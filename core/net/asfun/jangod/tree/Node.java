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

import java.io.Serializable;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;

public abstract class Node implements Serializable{

	private static final long serialVersionUID = 7323842986596895498L;
	
	int level = 0;
	Node parent = null;
	Node predecessor = null;
	Node successor = null;
	NodeList children = new NodeList();
	
	public Node parent() {
		return parent;
	}
	
	public Node predecessor() {
		return predecessor;
	}
	
	public Node successor() {
		return successor;
	}
	
	boolean replaceWithChildren(Node tobeReplace) {
		if ( tobeReplace.parent == null ) {
			return false;
		}
		return tobeReplace.parent.children.replace(tobeReplace, children);
	}
	
	public NodeList children() {
		return children;
	}
	
	boolean remove() {
		if ( parent != null ) {
			return parent.children.remove(this);
		}
		return false;
	}
	
	/**
	 * trusty call by TreeParser 
	 * @param node
	 */
	void add(Node node) {
		node.level = level + 1;
		node.parent = this;
		children.add(node);
	}
	
	Node treeNext() {
		if (children.size > 0 ) {
			return children.head;
		} else {
			return recursiveNext();
		}
	}

	Node recursiveNext() {
		if ( successor != null ) {
			return successor;
		} else {
			if ( parent != null ) {
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
		if ( parent != null ) {
			return parent.children.postend(this, node);
		}
		return false;
	}
	
	boolean leadMe(Node node) {
		if ( parent != null ) {
			return parent.children.preend(this, node);
		}
		return false;
	}
	
	void computeLevel(int baseLevel) {
//		for(int i=0;i<baseLevel;i++)
//			System.out.print("  ");
//		System.out.println("\\" + baseLevel);
		
		level = baseLevel;
		for( Node child : children ) {
			child.computeLevel(level + 1);
		}
	}
	
	public abstract String render(JangodInterpreter interpreter) throws InterpretException;

	public abstract String getName();
	
}
