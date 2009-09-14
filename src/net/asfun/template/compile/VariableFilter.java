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
package net.asfun.template.compile;

import java.util.List;

import net.asfun.template.parse.FilterParser;
import net.asfun.template.parse.ParserException;

import static net.asfun.template.util.logging.JangodLogger;

public class VariableFilter {
	
	public static Object compute(String varString, JangodCompiler compiler) throws CompilerException {
		FilterParser fp = new FilterParser(varString);
		try {
			fp.parse();
		} catch (ParserException e) {
			throw new CompilerException(e.getMessage());
		}
		Object var = compiler.retraceVariable(fp.getVariable());
		List<String> filters = fp.getFilters();
//		if ( filters.isEmpty() ) {
//			return var;
//		}
		List<String[]> argss = fp.getArgss();
		String[] args;
		Filter filter;
		for(int i=0; i<filters.size(); i++) {
			try {
				filter = FilterLibrary.getFilter(filters.get(i));
			} catch (CompilerException ce) {
				JangodLogger.warning("Skipping an unregistered filter >>> " + filters.get(i));
				continue;
			}
			args = argss.get(i);
			if ( args == null ) {
				var = filter.filter(var, compiler);
			} else {
				var = filter.filter(var, compiler, args);
			}
		}
		return var;
	}
}
