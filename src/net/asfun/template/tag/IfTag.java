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
package net.asfun.template.tag;

import java.util.List;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.JangodCompiler;
import net.asfun.template.compile.Node;
import net.asfun.template.compile.Tag;
import net.asfun.template.compile.VariableFilter;
//import net.asfun.template.util.HelperStringTokenizer;
import net.asfun.template.util.ObjectTruthValue;

/**
 * {% if a|f1:b,c|f2 %}
 * @author anysome
 *
 */
public class IfTag implements Tag {


	@Override
	public String compile(List<Node> carries, String helpers, JangodCompiler compiler)
			throws CompilerException {
		if ( helpers.length() == 0 ) {
			throw new CompilerException("if tag expects 1 helper >>> 0");
		}
		Object test = VariableFilter.compute(helpers, compiler);
		StringBuffer sb = new StringBuffer();
		if ( ObjectTruthValue.evaluate(test) ) {
			for(Node node : carries) {
//				if ( "[TagNode:else]".equals(node.toString()) ) {
				if ( "else".equals(node.toString()) ) {
					break;
				}
				sb.append(node.render(compiler));
			}
		} else {
			boolean inElse = false;
			for(Node node : carries) {
				if (inElse) {
					sb.append(node.render(compiler));
				}
//				if ( "[TagNode:else]".equals(node.toString()) ) {
				if ( "else".equals(node.toString()) ) {
					inElse = true;
				}
			}
		}
		return sb.toString();
	}

	@Override
	public String getEndTagName() {
		return "endif";
	}

	@Override
	public String getName() {
		return "if";
	}

}
