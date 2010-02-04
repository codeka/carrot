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

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.parse.FixedToken;

public class TextNode extends Node{

	private static final long serialVersionUID = 8488738480534354216L;
	private FixedToken master;
	static final String name = "Text_Node";

	public TextNode(FixedToken token) {
		super();
		master = token;
	}

	@Override
	public String render(JangodInterpreter interpreter) throws InterpretException {
		return master.output();
	}

	@Override
	public String toString() {
		return master.toString();
	}
	
	@Override
	public String getName() {
		return name;
	}
}
