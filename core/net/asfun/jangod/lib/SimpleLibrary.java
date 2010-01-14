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
package net.asfun.jangod.lib;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.util.HashMap;
import java.util.Map;


public abstract class SimpleLibrary<T> {

	private Map<String, T> lib = new HashMap<String, T>();
	
	protected SimpleLibrary() {
		initialize();
	}
	
	protected abstract void initialize();
	
	public T fetch(String item) {
		String key = item.toLowerCase();
		if ( lib.containsKey(key) ) {
			return lib.get(key);
		}
		JangodLogger.fine("Library doesn't contain >>> " + item);
		return null;
	}
	
	public void register(String item, T obj) {
		lib.put(item.toLowerCase(), obj);
	}
	
	public boolean isRegistered(String name) {
		return lib.containsKey(name.toLowerCase());
	}
}
