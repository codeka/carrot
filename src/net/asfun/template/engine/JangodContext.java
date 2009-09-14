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
package net.asfun.template.engine;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.SimpleBindings;


public class JangodContext implements ScriptContext{
	
	private Writer errw;
	private Writer wtr;
	private Reader rd;
	
	private Bindings engineScope = new SimpleBindings();
	private Bindings globalScope;
	
	public JangodContext(Bindings global) {
		globalScope = global;
	}
	
	@Override
	public Object getAttribute(String name) {
		if ( engineScope.containsKey(name)) {
			return engineScope.get(name);
		} else if ( globalScope.containsKey(name) ) {
			return globalScope.get(name);
		} else {
			return null;
		}
	}

	@Override
	public Object getAttribute(String name, int scope) {
		return getBindings(scope).get(name);
	}

	@Override
	public int getAttributesScope(String name) {
		if ( engineScope.containsKey(name) ) {
			return ENGINE_SCOPE;
		}
		if ( globalScope.containsKey(name) ) {
			return GLOBAL_SCOPE;
		}
		return -1;
	}

	@Override
	public Bindings getBindings(int scope) {
		switch (scope) {
			case ENGINE_SCOPE :
				return engineScope;
			case GLOBAL_SCOPE :
				return globalScope;
			default :
				throw new IllegalArgumentException("Illegal scope value.");
		}
	}

	@Override
	public Writer getErrorWriter() {
		return errw;
	}

	@Override
	public Reader getReader() {
		return rd;
	}

	@Override
	public List<Integer> getScopes() {
		List<Integer> scopes = new ArrayList<Integer>();
		scopes.add(ScriptContext.ENGINE_SCOPE);
		scopes.add(ScriptContext.GLOBAL_SCOPE);
		return scopes;
	}

	@Override
	public Writer getWriter() {
		return wtr;
	}

	@Override
	public Object removeAttribute(String name, int scope) {
		Bindings bindings = getBindings(scope);
		if ( bindings != null ) {
			return bindings.remove(name);
		}
		return null;
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		getBindings(scope).put(name, value);
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		if ( bindings == null ) {
			throw new NullPointerException("Bindings cannot be null.");
		}
		switch (scope) {
			case ENGINE_SCOPE :
				engineScope = bindings;
				break;
			case GLOBAL_SCOPE :
				globalScope = bindings;
				break;
			default :
				throw new IllegalArgumentException("Illegal scope value.");
		}
	}

	@Override
	public void setErrorWriter(Writer writer) {
		errw = writer;
	}

	@Override
	public void setReader(Reader reader) {
		rd = reader;
	}

	@Override
	public void setWriter(Writer writer) {
		wtr = writer;
	}

}
