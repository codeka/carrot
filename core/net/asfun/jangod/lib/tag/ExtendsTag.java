/**********************************************************************
Copyright (c) 2009 Asfun Net.

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
package net.asfun.jangod.lib.tag;

import java.io.IOException;

import net.asfun.jangod.base.Context;
import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.NodeList;
import net.asfun.jangod.util.HelperStringTokenizer;
import net.asfun.jangod.util.ListOrderedMap;

/**
 * {% extends "base.html" %}
 * {% extends var_fileName %}
 * @author anysome
 * TODO EXTENDS NESTED
 */
public class ExtendsTag implements Tag{

	final String TAGNAME = "extends";

	@Override
	public String interpreter(NodeList carries, String helpers, JangodInterpreter interpreter)
			throws InterpretException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if( helper.length != 1) {
			throw new InterpretException("Tag 'extends' expects 1 helper >>> " + helper.length);
		}
		String templateFile = interpreter.resolveString(helper[0]);
		try {
			String fullName = ResourceManager.getFullName(templateFile, 
					interpreter.getWorkspace(), interpreter.getConfiguration().getWorkspace());
			Node node = interpreter.getContext().getApplication().getParseResult(
					fullName, interpreter.getConfiguration().getEncoding() );
			
			
			ListOrderedMap blockList = new ListOrderedMap();
			interpreter.assignRuntimeScope(Context.BLOCK_LIST, blockList, 1);
			JangodInterpreter parent = interpreter.clone();
			interpreter.assignRuntimeScope(Context.CHILD_FLAG, true, 1);
			parent.assignRuntimeScope(Context.PARENT_FLAG, true, 1);
			String semi = parent.render(node);
			interpreter.assignRuntimeScope(Context.SEMI_RENDER, semi, 1);
			return "";
		} catch (IOException e) {
			throw new InterpretException(e.getMessage());
		}
	}

	@Override
	public String getEndTagName() {
		return null;
	}

	@Override
	public String getName() {
		return TAGNAME;
	}

}
