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
package net.asfun.jangod.script;

import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.SimpleBindings;

public class JangodEngineFactory implements ScriptEngineFactory {
	
	protected Bindings globalBindings = new SimpleBindings();
	
	public void setGlobalBindings(Bindings bindings) {
		globalBindings = bindings;
	}

	@Override
	public String getEngineName() {
		return "ASFUN Jangod template engine";
	}

	@Override
	public String getEngineVersion() {
		return "0.28";
	}

	@Override
	public List<String> getExtensions() {
		List<String> ext = new ArrayList<String>();
		ext.add("god");
		ext.add("tpl");
		ext.add("html");
		ext.add("jangod");
		return ext;
	}

	@Override
	public String getLanguageName() {
		return "Jangod";
	}

	@Override
	public String getLanguageVersion() {
		return "1.0";
	}

	@Override
	public String getMethodCallSyntax(String obj, String m, String... args) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getMimeTypes() {
		return new ArrayList<String>();
	}

	@Override
	public List<String> getNames() {
		List<String> names = new ArrayList<String>();
		names.add("Jangod");
		names.add("Django");
		names.add("Jinja");
		return names;
	}

	@Override
	public String getOutputStatement(String toDisplay) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getParameter(String key) {
		if ("THREADING".equals(key))
			return "THREAD-ISOLATED";
		else if (ScriptEngine.ENGINE.equals(key))
			return getEngineName();
		else if (ScriptEngine.ENGINE_VERSION.equals(key))
			return getEngineVersion();
		else if (ScriptEngine.NAME.equals(key))
			return getEngineName();
		else if (ScriptEngine.LANGUAGE.equals(key))
			return getLanguageName();
		else if (ScriptEngine.LANGUAGE_VERSION.equals(key))
			return getLanguageVersion();
		else
			return null;
	}

	@Override
	public String getProgram(String... statements) {
		StringBuilder buff = new StringBuilder();
		for (String statement : statements) {
			buff.append(statement).append("\n");
		}
		return buff.toString();
	}

	@Override
	public ScriptEngine getScriptEngine() {
		return new JangodEngine(this);
	}

	@Override
	public String toString() {
		return getEngineName() + " v" + getEngineVersion();
	}

}
