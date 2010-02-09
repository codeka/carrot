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

import java.util.List;

import net.asfun.jangod.base.Constants;
import net.asfun.jangod.lib.Filter;
import net.asfun.jangod.lib.FilterLibrary;
import net.asfun.jangod.parse.FilterParser;
import net.asfun.jangod.parse.ParseException;

import static net.asfun.jangod.util.logging.JangodLogger;

public class VariableFilter {
	
	public static Object compute(String varString, JangodInterpreter interpreter) throws InterpretException {
		if ( ( varString.startsWith(Constants.STR_SINGLE_QUOTE) &&varString.endsWith(Constants.STR_SINGLE_QUOTE) ) || 
				( varString.startsWith(Constants.STR_DOUBLE_QUOTE) && varString.endsWith(Constants.STR_DOUBLE_QUOTE) ) ) {
			return varString.substring(1, varString.length()-1);
		}
		FilterParser fp = new FilterParser(varString);
		try {
			fp.parse();
		} catch (ParseException e) {
			throw new InterpretException(e.getMessage());
		}
		Object var = interpreter.retraceVariable(fp.getVariable());
		List<String> filters = fp.getFilters();
		if ( filters.isEmpty() ) {
			return var;
		}
		List<String[]> argss = fp.getArgss();
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
				var = filter.filter(var, interpreter);
			} else {
				var = filter.filter(var, interpreter, args);
			}
		}
		return var;
	}
}
