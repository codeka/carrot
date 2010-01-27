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
//import net.asfun.template.util.HelperStringTokenizer;
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
	public String interpreter(List<Node> carries, String helpers, JangodInterpreter interpreter)
			throws InterpretException {
		if ( helpers.length() == 0 ) {
			throw new InterpretException("Tag 'if' expects 1 helper >>> 0");
		}
		Object test = VariableFilter.compute(helpers, interpreter);
		StringBuffer sb = new StringBuffer();
		if ( ObjectTruthValue.evaluate(test) ) {
			for(Node node : carries) {
				if ( ElseTag.ELSE.equals(node.toString()) ) {
					break;
				}
				sb.append(node.render(interpreter));
			}
		} else {
			boolean inElse = false;
			for(Node node : carries) {
				if (inElse) {
					sb.append(node.render(interpreter));
				}
				if (  ElseTag.ELSE.equals(node.toString()) ) {
					inElse = true;
				}
			}
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
