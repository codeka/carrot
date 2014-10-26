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
import java.io.Writer;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.interpret.VariableFilter;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.NodeList;
import net.asfun.jangod.util.ObjectTruthValue;

/**
 * {% if a|f1:b,c|f2 %}
 * @author anysome
 *
 */
public class IfTag implements Tag {

	final String TAGNAME = "if";
	final String ENDTAGNAME = "endif";

	@Override
	public void interpreter(NodeList carries, String helpers, JangodInterpreter interpreter,
			Writer writer) throws InterpretException, IOException {
		if ( helpers.length() == 0 ) {
			throw new InterpretException("Tag 'if' expects 1 helper >>> 0");
		}
		Object test = VariableFilter.compute(helpers, interpreter);
		if ( ObjectTruthValue.evaluate(test) ) {
			for(Node node : carries) {
				if ( ElseTag.ELSE.equals(node.getName()) ) {
					break;
				}
				node.render(interpreter, writer);
			}
		} else {
			boolean inElse = false;
			for(Node node : carries) {
				if (inElse) {
					node.render(interpreter, writer);
				}
				if (  ElseTag.ELSE.equals(node.getName()) ) {
					inElse = true;
				}
			}
		}
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
