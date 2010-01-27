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

import java.io.Reader;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import net.asfun.jangod.base.Context;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.parse.TokenParser;
import net.asfun.jangod.parse.ParseException;

import static net.asfun.jangod.util.logging.*;

public class JangodEngine implements ScriptEngine {
	
	private String defaultBindings = "javax.script.SimpleBindings";
	private JangodEngineFactory factory;
	private ScriptContext context;
	
	public JangodEngine() {
		factory = new JangodEngineFactory();
		context = new JangodContext(factory.globalBindings);
		initGlobalData();
	}
	
	public JangodEngine(JangodEngineFactory fac) {
		factory = fac;
		context = new JangodContext(factory.globalBindings);
		initGlobalData();
	}
	
	private void initGlobalData() {
		//TODO set default var like version, etc.
	}

	@Override
	public Bindings createBindings() {
		try {
			return (Bindings) Class.forName(defaultBindings).newInstance();
		} catch (InstantiationException e) {
			JangodLogger.log(Level.SEVERE, e.getMessage(), e.getCause());
		} catch (IllegalAccessException e) {
			JangodLogger.log(Level.SEVERE, e.getMessage(), e.getCause());
		} catch (ClassNotFoundException e) {
			JangodLogger.log(Level.SEVERE, e.getMessage(), e.getCause());
		} catch (Exception e) {
			JangodLogger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
		return new SimpleBindings();
	}

	@Override
	public Object eval(String script, ScriptContext ctx) throws ScriptException {
		TokenParser parser = new TokenParser(script);
		JangodInterpreter interpreter = new JangodInterpreter((Context) ctx);
		try {
			return interpreter.render(parser);
		} catch (InterpretException e) {
			throw new ScriptException(e.getMessage());
		}
	}

	@Override
	public Object eval(Reader reader, ScriptContext ctx) throws ScriptException {
		TokenParser parser;
		try {
			parser = new TokenParser(reader);
		} catch (ParseException e) {
			throw new ScriptException(e.getMessage());
		}
		JangodInterpreter interpreter = new JangodInterpreter((Context) ctx);
		try {
			return interpreter.render(parser);
		} catch (InterpretException e) {
			throw new ScriptException(e.getMessage());
		}
	}

	@Override
	public ScriptEngineFactory getFactory() {
		return factory;
	}

	@Override
	public Object eval(String script) throws ScriptException {
		TokenParser parser = new TokenParser(script);
		JangodInterpreter interpreter = new JangodInterpreter((Context) context);
		try {
			return interpreter.render(parser);
		} catch (InterpretException e) {
			throw new ScriptException(e.getMessage());
		}
	}

	@Override
	public Object eval(Reader reader) throws ScriptException {
		TokenParser parser;
		try {
			parser = new TokenParser(reader);
		} catch (ParseException e) {
			throw new ScriptException(e.getMessage());
		}
		JangodInterpreter interpreter = new JangodInterpreter((Context) context);
		try {
			return interpreter.render(parser);
		} catch (InterpretException e) {
			throw new ScriptException(e.getMessage());
		}
	}

	@Override
	public Object eval(String script, Bindings n) throws ScriptException {
		TokenParser parser = new TokenParser(script);
		ScriptContext ctx = new JangodContext(factory.globalBindings);
		ctx.setBindings(n, ScriptContext.ENGINE_SCOPE);
		JangodInterpreter interpreter = new JangodInterpreter((Context) ctx);
		try {
			return interpreter.render(parser);
		} catch (InterpretException e) {
			throw new ScriptException(e.getMessage());
		}
	}

	@Override
	public Object eval(Reader reader, Bindings n) throws ScriptException {
		TokenParser parser;
		try {
			parser = new TokenParser(reader);
		} catch (ParseException e) {
			throw new ScriptException(e.getMessage());
		}
		
		ScriptContext ctx = new JangodContext(factory.globalBindings);
		ctx.setBindings(n, ScriptContext.ENGINE_SCOPE);
		JangodInterpreter interpreter = new JangodInterpreter((Context) ctx);
		try {
			return interpreter.render(parser);
		} catch (InterpretException e) {
			throw new ScriptException(e.getMessage());
		}
	}

	@Override
	public Object get(String key) {
		return getBindings(ScriptContext.ENGINE_SCOPE).get(key);
	}

	@Override
	public Bindings getBindings(int scope) {
		return context.getBindings(scope);
	}

	@Override
	public ScriptContext getContext() {
		return context;
	}

	@Override
	public void put(String key, Object value) {
		getBindings(ScriptContext.ENGINE_SCOPE).put(key, value);
	}

	@Override
	public void setBindings(Bindings bindings, int scope) {
		context.setBindings(bindings, scope);
	}

	@Override
	public void setContext(ScriptContext scontext) {
		context = scontext;
	}

}
