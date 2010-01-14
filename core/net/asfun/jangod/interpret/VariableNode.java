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
package net.asfun.jangod.interpret;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.util.List;

import net.asfun.jangod.lib.Filter;
import net.asfun.jangod.lib.FilterLibrary;
import net.asfun.jangod.parse.EchoToken;
import net.asfun.jangod.util.ObjectValue;

public class VariableNode implements Node{
	
	public VariableNode(EchoToken tk, int lvl) {
		token = tk;
		level = lvl;
	}

	private int level;
	private EchoToken token;

	@Override
	public String render(JangodInterpreter interperter) throws InterpretException {
		interperter.setLevel(level);
		Object var = interperter.retraceVariable(token.getVariable());
		//filters
		List<String> filters = token.getFilters();
//		if ( filters.isEmpty() ) {
//			return ObjectValue.printable(var);
//		}
		List<String[]> argss = token.getArgss();
		String[] args;
		Filter filter;
		for(int i=0; i<filters.size(); i++) {
			filter = FilterLibrary.getFilter(filters.get(i));
			if ( filter == null ) {
				JangodLogger.warning("skipping an unregistered filter >>> " + filters.get(i));
				continue;
			}
			args = argss.get(i);
			if ( args == null ) {
				var = filter.filter(var, interperter);
			} else {
				var = filter.filter(var, interperter, args);
			}
		}
		return ObjectValue.printable(var);
	}

}
