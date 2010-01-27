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

import java.util.List;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.interpret.VariableFilter;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.node.Node;
import net.asfun.jangod.util.ForLoop;
import net.asfun.jangod.util.HelperStringTokenizer;
import net.asfun.jangod.util.ObjectIterator;

/**
 * {% for a in b|f1:d,c %}	
 * @author anysome
 *
 */
public class ForTag implements Tag {
	
	final String LOOP = "loop";
	final String TAGNAME = "for";
	final String ENDTAGNAME = "endfor";
	
	@Override
	public String interpreter(List<Node> carries, String helpers, JangodInterpreter interpreter) throws InterpretException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if ( helper.length != 3 ) {
			throw new InterpretException("Tag 'for' expects 3 helpers >>> " + helper.length);
		}
		String item = helper[0];
		Object collection = VariableFilter.compute( helper[2], interpreter);
		ForLoop loop = ObjectIterator.getLoop(collection);
		
		int level = interpreter.getLevel() + 1;
		interpreter.assignRuntimeScope(LOOP, loop, level);
		StringBuffer buff = new StringBuffer();
		while ( loop.hasNext() ) {
			//set item variable
			interpreter.assignRuntimeScope(item, loop.next(), level);
			for(Node node : carries) {
				buff.append(node.render(interpreter));
			}
		}
		return buff.toString();
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
