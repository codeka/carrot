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
package net.asfun.template.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


public class ObjectIterator {
	
	public static ForLoop getLoop(Object obj) {
		if ( obj == null ) {
			return new ForLoop(new ArrayList<Object>().iterator(), 0);
		}
		//collection
		if( obj instanceof Collection ) {
			Collection<?> clt = (Collection<?>)obj;
			return new ForLoop(clt.iterator(), clt.size());
		}
		//array
		if( obj.getClass().isArray() ) {
			return new ForLoop(new ArrayIterator(obj), Array.getLength(obj));
		}
		//map
		if ( obj instanceof Map ) {
			Collection<?> clt = ((Map<?,?>)obj).values();
			return new ForLoop(clt.iterator(), clt.size());
		}
		//iterable,iterator
		if ( obj instanceof Iterable ) {
			//TODO fix the loop if length is unknown.
			return new ForLoop(((Iterable<?>)obj).iterator());
		}
		if ( obj instanceof Iterator ) {
			//TODO fix the loop if length is unknown.
			return new ForLoop((Iterator<?>)obj);
		}
		//others
		ArrayList<Object> res = new ArrayList<Object>();
		res.add(obj);
		return new ForLoop(res.iterator(), 1);
	}

}
