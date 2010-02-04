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

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.NodeList;
import net.asfun.jangod.util.ListOrderedMap;
import net.asfun.jangod.util.HelperStringTokenizer;

/**
 * {% block name %}
 * @author anysome
 * TODO EXTENDS NESTED
 */

public class BlockTag implements Tag{
	
	final String BLOCKNAMES = "'BLK\"NAMES";
	final String TAGNAME = "block";
	final String ENDTAGNAME = "endblock";

	@SuppressWarnings("unchecked")
	@Override
	public String interpreter(NodeList carries, String helpers, JangodInterpreter interpreter)
			throws InterpretException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if( helper.length != 1) {
			throw new InterpretException("Tag 'block' expects 1 helper >>> " + helper.length);
		}
		String blockName = interpreter.resolveString(helper[0]);
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
		Object isChild = interpreter.fetchRuntimeScope(JangodInterpreter.CHILD_FLAG, 1);
		if ( isChild != null ) {
			ListOrderedMap blockList = (ListOrderedMap) interpreter.fetchRuntimeScope(JangodInterpreter.BLOCK_LIST, 1);
			//check block was defined in parent
			if ( ! blockList.containsKey(blockName) ) {
				throw new InterpretException("Dosen't define block in extends parent with name >>> " + blockName);
			}
			//cover parent block content with child's.
			blockList.put(blockName, getBlockContent(carries, interpreter));
			return "";
		}
		Object isParent = interpreter.fetchRuntimeScope(JangodInterpreter.PARENT_FLAG, 1);
		if ( isParent != null) {
			//save block content to engine, and return identify
			ListOrderedMap blockList = (ListOrderedMap) interpreter.fetchRuntimeScope(JangodInterpreter.BLOCK_LIST, 1);
			blockList.put(blockName, getBlockContent(carries, interpreter));
			return JangodInterpreter.SEMI_BLOCK + blockName;
		}
		return getBlockContent(carries, interpreter);
	}
	
	private String getBlockContent(NodeList carries, JangodInterpreter interpreter) throws InterpretException {
		StringBuffer sb = new StringBuffer();
		for(Node node : carries) {
			sb.append(node.render(interpreter));
		}
		return sb.toString();
	}

	@Override
	public String getEndTagName() {
		return ENDTAGNAME;
	}

	@Override
	public String getName() {
		return TAGNAME;
	}

}
