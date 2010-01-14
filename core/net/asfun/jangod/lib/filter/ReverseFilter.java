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

import java.lang.reflect.Array;
import java.util.Collection;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;

public class ReverseFilter implements Filter{

	@SuppressWarnings("unchecked")
	@Override
	public Object filter(Object object, JangodInterpreter interpreter, String... arg)
			throws InterpretException {
		if ( object == null ) {
			return null;
		}
		//collection
		if ( object instanceof Collection ) {
			Object[] origin = ((Collection<?>)object).toArray();
			int length = origin.length;
			Object[] res = new Object[length];
			length--;
			for(int i=0; i<=length; i++) {
				res[i] = origin[length-i];
			}
			return res;
		}
		//array
		if ( object.getClass().isArray() ) {
			int length = Array.getLength(object);
			Object[] res = new Object[length];
			length--;
			for(int i=0; i<=length; i++) {
				res[i] = Array.get(object, length-i);
			}
			return res;
		}
		//string
		if ( object instanceof String ) {
			String origin = (String)object;
			int length = origin.length();
			char[] res = new char[length];
			length--;
			for(int i=0; i<=length; i++) {
				res[i] = origin.charAt(length-i);
			}
			return String.valueOf(res);
		}
		JangodLogger.warning("filter contain can't be applied to >>> " + object.getClass().getName());
		return object;
	}

	@Override
	public String getName() {
		return "reverse";
	}

}
