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

/**
 * {% ifchange var %}
 * @author anysome
 *
 */
public class IfchangedTag implements Tag{
	
	private static final String LASTKEY = "'IF\"CHG";
	

	@Override
	public String compile(List<Node> carries, String helpers, JangodCompiler compiler)
			throws CompilerException {
		if ( helpers.length() == 0 ) {
			throw new CompilerException("ifchanged tag expects 1 helper >>> 0");
		}
		boolean isChanged = true;
		String var = helpers;
		Object older = compiler.fetchRuntimeScope(LASTKEY + var);
		Object test = compiler.retraceVariable(var);
		if ( older == null ) {
			if ( test == null ) {
				isChanged = false;
			}
		} else if ( older.equals(test) ) {
			isChanged = false;
		}
		compiler.assignRuntimeScope(LASTKEY + var, test);
		if ( isChanged ) {
			StringBuffer sb = new StringBuffer();
			for(Node node : carries) {
				sb.append(node.render(compiler));
			}
			return sb.toString();
		}
		return "";
	}

	@Override
	public String getEndTagName() {
		return "endif";
	}

	@Override
	public String getName() {
		return "ifchanged";
	}

}
