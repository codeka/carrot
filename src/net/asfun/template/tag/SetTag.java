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
import net.asfun.template.util.HelperStringTokenizer;

/**
 * {% set varName post.id|equal:'12' %}
 * @author anysome
 *
 */
public class SetTag implements Tag{


	@Override
	public String getName() {
		return "set";
	}

	@Override
	public String compile(List<Node> carries, String helpers, JangodCompiler compiler) throws CompilerException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if ( helper.length != 2 ) {
			throw new CompilerException("set inst expects 2 helper >>> " + helper.length);
		}
		Object value = VariableFilter.compute(helper[1], compiler);
		compiler.assignEngineScope(helper[0], value);
		return "";
	}

	@Override
	public String getEndTagName() {
		return null;
	}

}
