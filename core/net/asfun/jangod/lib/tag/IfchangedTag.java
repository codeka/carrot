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
import net.asfun.jangod.interpret.Node;
import net.asfun.jangod.lib.Tag;

/**
 * {% ifchange var %}
 * @author anysome
 *
 */
public class IfchangedTag implements Tag{
	
	private static final String LASTKEY = "'IF\"CHG";
	

	@Override
	public String interpreter(List<Node> carries, String helpers, JangodInterpreter interpreter)
			throws InterpretException {
		if ( helpers.length() == 0 ) {
			throw new InterpretException("Tag 'ifchanged' expects 1 helper >>> 0");
		}
		boolean isChanged = true;
		String var = helpers;
		Object older = interpreter.fetchRuntimeScope(LASTKEY + var);
		Object test = interpreter.retraceVariable(var);
		if ( older == null ) {
			if ( test == null ) {
				isChanged = false;
			}
		} else if ( older.equals(test) ) {
			isChanged = false;
		}
		interpreter.assignRuntimeScope(LASTKEY + var, test);
		if ( isChanged ) {
			StringBuffer sb = new StringBuffer();
			for(Node node : carries) {
				sb.append(node.render(interpreter));
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
