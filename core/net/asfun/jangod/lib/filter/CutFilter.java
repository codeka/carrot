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

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;
import net.asfun.jangod.util.ObjectValue;

public class CutFilter implements Filter{

	@Override
	public Object filter(Object object, JangodInterpreter interpreter, String... arg)
			throws InterpretException {
		if ( arg.length != 1 ) {
			throw new InterpretException("filter cut expects 1 arg >>> " + arg.length);
		}
		String cutee = interpreter.resolveString(arg[0]);
		String origin = ObjectValue.printable(object);
		return origin.replace(cutee, BLANK_STRING);
	}

	@Override
	public String getName() {
		return "cut";
	}

}
