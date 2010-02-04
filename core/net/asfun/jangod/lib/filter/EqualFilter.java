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

import net.asfun.jangod.base.Constants;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;
import net.asfun.jangod.util.ObjectStringEqual;

public class EqualFilter implements Filter{

	@Override
	public Object filter(Object object, JangodInterpreter interpreter, String... arg) throws InterpretException {
		if ( arg.length != 1 ) {
			throw new InterpretException("filter equal expects 1 arg >>> " + arg.length);
		}
		Object argObj ;
		boolean isNull = false;
		if ( arg[0].startsWith(Constants.STR_SINGLE_QUOTE) || arg[0].startsWith(Constants.STR_DOUBLE_QUOTE) ) {
			argObj = arg[0].substring(1, arg[0].length()-1);
		} else {
			argObj = interpreter.retraceVariable(arg[0]);
			if ( isNull = argObj == null ) {
				argObj = arg[0];
			}
		}
		if ( object == null ) {
			return isNull;
		} else {
			return ObjectStringEqual.evaluate(object, argObj);
		}
	}

	@Override
	public String getName() {
		return "equal";
	}

}
