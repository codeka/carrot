/**********************************************************************
Copyright (c) 2010 Asfun Net.

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
package net.asfun.jangod.base;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Context {

	public static final int SCOPE_GLOBAL = 1;
	public static final int SCOPE_SESSION = 2;
	
	public static final String CHILD_FLAG = "'IS\"CHILD";
	public static final String PARENT_FLAG = "'IS\"PARENT";
	public static final String INSERT_FLAG = "'IS\"INSERT";
	public static final String SEMI_RENDER = "'SEMI\"FORMAL";
	public static final String BLOCK_LIST = "'BLK\"LIST";
	public static final String SEMI_BLOCK = "<K2C9OL7B>";
	
	Configuration config;
	protected Map<String, Object> globalBindings;
	protected Map<String, Object> sessionBindings;
	
	public Context() {
		config = Configuration.getDefault();
		globalBindings = new Hashtable<String, Object>();
		sessionBindings = new HashMap<String, Object>();
	}
	
	public Context(Configuration configuration) {
		if ( configuration == null ) {
			configuration = Configuration.getDefault();
		}
		config = configuration;
		globalBindings = new Hashtable<String, Object>();
		sessionBindings = new HashMap<String, Object>();
	}
	
	public Configuration getConfiguration() {
		return config;
	}

	public Object getAttribute(String varName, int scope) {
		switch ( scope ) {
		case SCOPE_GLOBAL :
			return globalBindings.get(varName);
		case SCOPE_SESSION :
			return sessionBindings.get(varName);
		default :
			return null;
		}
	}

	public Object getAttribute(String varName) {
		if ( sessionBindings.containsKey(varName) ) {
			return sessionBindings.get(varName);
		} 
		else if ( globalBindings.containsKey(varName) ) {
			return globalBindings.get(varName);
		} 
		return null;	
	}
	
	public void setAttribute(String varName, Object value, int scope) {
		switch ( scope ) {
		case SCOPE_GLOBAL :
			globalBindings.put(varName, value);
			break;
		case SCOPE_SESSION :
			sessionBindings.put(varName, value);
			break;
		default :
			throw new IllegalArgumentException("Illegal scope value.");
		}
	}
	
	public void initBindings(Map<String, Object> bindings, int scope) {
		switch ( scope ) {
		case SCOPE_GLOBAL :
			globalBindings = bindings;
			break;
		case SCOPE_SESSION :
			sessionBindings = bindings;
			break;
		default :
			throw new IllegalArgumentException("Illegal scope value.");
		}
	}
	
	public void setAttributes(Map<String, Object> bindings, int scope) {
		switch ( scope ) {
		case SCOPE_GLOBAL :
			globalBindings.putAll(bindings);
			break;
		case SCOPE_SESSION :
			sessionBindings.putAll(bindings);
			break;
		default :
			throw new IllegalArgumentException("Illegal scope value.");
		}
	}

	public void reset(int scope) {
		switch ( scope ) {
		case SCOPE_GLOBAL :
			globalBindings.clear();
			break;
		case SCOPE_SESSION :
			sessionBindings.clear();
			break;
		default :
			throw new IllegalArgumentException("Illegal scope value.");
		}
	}
	
}
