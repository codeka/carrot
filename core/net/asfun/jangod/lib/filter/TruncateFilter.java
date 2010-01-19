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
package net.asfun.jangod.lib.filter;

import static net.asfun.jangod.util.logging.JangodLogger;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;

public class TruncateFilter implements Filter{

	final String ENDS = "...";
	
	@Override
	public Object filter(Object object, JangodInterpreter interpreter, String... arg) throws InterpretException {
		if ( object instanceof String ) {
			int length = 100;
			String ends = ENDS;
			if ( arg.length > 0 ) {
				try {
					length = Integer.valueOf(interpreter.resolveString(arg[0]));
				} catch (Exception e) {
					JangodLogger.warning("filter truncate get length error use default >>> 100");
				}
			}
			if ( arg.length > 1 ) {
				ends = interpreter.resolveString(arg[1]);
			}
			String string = (String)object;
			if ( string.length() > length ) {
				return string.substring(0, length) + ends;
			} else {
				return string;
			}
		}
		return object;
	}

	@Override
	public String getName() {
		return "truncate";
	}

}
