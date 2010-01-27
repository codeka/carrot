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
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.node.Node;
import net.asfun.jangod.util.HelperStringTokenizer;

/**
 * {% cycle a,b,c %}   
 * {% cycle a,'b',c as d %} 
 * {% cycle d %}
 * @author anysome
 *
 */
public class CycleTag implements Tag{
	
	final String LOOP_INDEX = "loop.index";
	final String TAGNAME = "cycle";

	@Override
	public String interpreter(List<Node> carries, String helpers, JangodInterpreter interpreter)
			throws InterpretException {
		String[] values;
		String var = null;
		HelperStringTokenizer tk = new HelperStringTokenizer(helpers);
		//TODO tokenize in one time
		String[] helper = tk.allTokens();
		if (helper.length == 1) {
			HelperStringTokenizer items = new HelperStringTokenizer(helper[0]);
			items.splitComma(true);
			values = items.allTokens();
			Integer forindex = (Integer) interpreter.retraceVariable(LOOP_INDEX);
			if (forindex == null) {
				forindex = 0;
			}
			if (values.length == 1) {
				var = values[0];
				values = (String[]) interpreter.retraceVariable(var);
				if ( values == null ) {
					return interpreter.resolveString(var);
				}
			} else {
				for(int i=0; i<values.length; i++) {
					values[i] = interpreter.resolveString(values[i]);
				}
			}
			return values[forindex % values.length];
		} else if (helper.length == 3) {
			HelperStringTokenizer items = new HelperStringTokenizer(helper[0]);
			items.splitComma(true);
			values = items.allTokens();
			for(int i=0; i<values.length; i++) {
				values[i] = interpreter.resolveString(values[i]);
			}
			var = helper[2];
			interpreter.assignRuntimeScope(var, values);
			return BLANK_STRING;
		} else {
			throw new InterpretException("Tag 'cycle' expects 1 or 3 helper(s) >>> " + helper.length);
		}
	}

	@Override
	public String getEndTagName() {
		return null;
	}

	@Override
	public String getName() {
		return TAGNAME;
	}

}
