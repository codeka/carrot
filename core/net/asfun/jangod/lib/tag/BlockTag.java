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

import java.util.ArrayList;
import java.util.List;

import net.asfun.jangod.base.Context;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.interpret.Node;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.util.ListOrderedMap;
//import net.asfun.template.util.HelperStringTokenizer;

/**
 * {% block name %}
 * @author anysome
 *
 */

public class BlockTag implements Tag{
	
	private static final String BLOCKNAMES = "'BLK\"NAMES";

	@SuppressWarnings("unchecked")
	@Override
	public String compile(List<Node> carries, String helpers, JangodInterpreter interpreter)
			throws InterpretException {
		//init
//		String[] helper = new HelperStringTokenizer(helpers).allTokens();
//		if( helper.length != 1) {
//			throw new CompilerException("extends tag expects 1 helper >>> " + helper.length);
//		}
		String blockName = helpers;
		if ( helpers.length() == 0 ) {
			throw new InterpretException("Tag 'block' expects 1 helper >>> 0");
		}
		//check block name is unique
		List<String> blockNames = (List<String>) interpreter.fetchRuntimeScope(BLOCKNAMES ,1);
		if ( blockNames == null ) {
			blockNames = new ArrayList<String>();
		}
		if ( blockNames.contains(blockName) ) {
			throw new InterpretException("Can't redefine the block with name >>> " + blockName);
		} else {
			blockNames.add(blockName);
			interpreter.assignRuntimeScope(BLOCKNAMES, blockNames, 1);
		}
		Object isChild = interpreter.fetchRuntimeScope(Context.CHILD_FLAG, 1);
		if ( isChild != null ) {
			ListOrderedMap blockList = (ListOrderedMap) interpreter.fetchSessionScope(Context.BLOCK_LIST);
			//check block was defined in parent
			if ( ! blockList.containsKey(blockName) ) {
				throw new InterpretException("Dosen't define block in extends parent with name >>> " + blockName);
			}
			//cover parent block content with child's.
			blockList.put(blockName, getBlockContent(carries, interpreter));
			return "";
		}
		Object isParent = interpreter.fetchRuntimeScope(Context.PARENT_FLAG, 1);
		if ( isParent != null) {
			//save block content to engine, and return identify
			ListOrderedMap blockList = (ListOrderedMap) interpreter.fetchSessionScope(Context.BLOCK_LIST);
			blockList.put(blockName, getBlockContent(carries, interpreter));
			return Context.SEMI_BLOCK + blockName;
		}
		return getBlockContent(carries, interpreter);
	}
	
	private String getBlockContent(List<Node> carries, JangodInterpreter interpreter) throws InterpretException {
		StringBuffer sb = new StringBuffer();
		for(Node node : carries) {
			sb.append(node.render(interpreter));
		}
		return sb.toString();
	}

	@Override
	public String getEndTagName() {
		return "endblock";
	}

	@Override
	public String getName() {
		return "block";
	}

}
