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
import net.asfun.template.compile.Tag;
import net.asfun.template.compile.VariableFilter;
import net.asfun.template.util.ForLoop;
import net.asfun.template.util.HelperStringTokenizer;
import net.asfun.template.util.ObjectIterator;
import net.asfun.template.compile.Node;

/**
 * {% for a in b|f1:d,c %}	
 * @author anysome
 *
 */
public class ForTag implements Tag {
	

	@Override
	public String compile(List<Node> carries, String helpers, JangodCompiler compiler) throws CompilerException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if ( helper.length != 3 ) {
			throw new CompilerException("for tag expects 3 helpers >>> " + helper.length);
		}
		String item = helper[0];
		Object collection = VariableFilter.compute( helper[2], compiler);
		ForLoop loop = ObjectIterator.getLoop(collection);
		
		int level = compiler.getLevel() + 1;
		compiler.assignRuntimeScope("loop", loop, level);
		StringBuffer buff = new StringBuffer();
		while ( loop.hasNext() ) {
			//set item variable
			compiler.assignRuntimeScope(item, loop.next(), level);
			for(Node node : carries) {
				buff.append(node.render(compiler));
			}
		}
		return buff.toString();
	}

	@Override
	public String getEndTagName() {
		return "endfor";
	}

	@Override
	public String getName() {
		return "for";
	}

}
