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
package net.asfun.template.filter;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.Filter;
import net.asfun.template.compile.JangodCompiler;
import net.asfun.template.util.ObjectStringEqual;

public class ContainFilter implements Filter{

	@Override
	public Object filter(Object object, JangodCompiler compiler, String... arg)
			throws CompilerException {
		if ( object == null ) {
			return false;
		}
		if ( arg.length != 1 ) {
			throw new CompilerException("filter contain expects 1 arg >>> " + arg.length);
		}
		Object argObj ;
		boolean isNull = false;
		if ( arg[0].startsWith("'") || arg[0].startsWith("\"") ) {
			argObj = arg[0].substring(1, arg[0].length()-1);
		} else {
			argObj = compiler.retraceVariable(arg[0]);
			if ( isNull = argObj == null ) {
				argObj = arg[0];
			}
		}
		//iterable
		if ( object instanceof Iterable ) {
			Iterator<?> it = ((Iterable<?>)object).iterator();
			return iteratorContain(it, isNull, argObj);
		}
		//array
		if ( object.getClass().isArray() ) {
			int length = Array.getLength(object);
			Object item;
			for(int i=0; i<length; i++) {
				item = Array.get(object, i);
				if ( item == null ) {
					if ( isNull )
						return true;
				} else if ( ObjectStringEqual.evaluate(item, argObj) ){
					return true;
				}
			}
			return false;
		}
		//map
		if( object instanceof Map ) {
			Iterator<?> it = ((Map<?,?>)object).values().iterator();
			return iteratorContain(it, isNull, argObj);
		}
		//string
		if ( object instanceof String ) {
			return object.toString().contains(argObj.toString());
		}
		//iterator
		if ( object instanceof Iterator ) {
			return iteratorContain((Iterator<?>)object, isNull, argObj);
		}
		throw new CompilerException("filter contain can't be applied to >>> " + object.getClass().getName());
	}

	@Override
	public String getName() {
		return "contain";
	}
	
	private boolean iteratorContain(Iterator<?> it, boolean isNull, Object argObj) {
		Object item;
		while(it.hasNext()) {
			item = it.next();
			if ( item == null ) {
				if ( isNull ) {
					return true;
				}
			}
			else if ( ObjectStringEqual.evaluate(item, argObj) ) {
				return true;
			}
		}
		return false;
	}

}
