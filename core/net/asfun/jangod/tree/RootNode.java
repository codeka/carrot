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

import java.io.Writer;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;

public class RootNode extends Node{

	private static final long serialVersionUID = 97675838726004658L;
	static final String TREE_ROOT_END = "anysome";

	RootNode(Application app) {
		super(app);
	}

	@Override
	public void render(JangodInterpreter interpreter, Writer writer) throws InterpretException {
		throw new UnsupportedOperationException("Please render RootNode by interpreter");
	}
	
	@Override
	public String getName() {
		return TREE_ROOT_END;
	}
	
	@Override
	public Node clone() {
		Node clone = new RootNode(app);
		clone.children = this.children.clone(clone);
		return clone;
	}
}
