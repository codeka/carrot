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
package net.asfun.template;

import java.io.IOException;
import java.io.Writer;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import net.asfun.template.bin.CacheBindings;
import net.asfun.template.bin.LazyBindings;
import net.asfun.template.engine.JangodEngineFactory;
import net.asfun.template.util.TemplateLoader;


public class Template {
	
	public static final int NORMBINDINGS = 0;
	public static final int CACHEBINDINGS = 1;
	public static final int LAZYBINDINGS = 2;
	
	private Configuration config;
	private JangodEngineFactory factory;
	private TemplateLoader loader;
	
	public Template(Configuration conf) {
		config = conf;
		init();
	}
	
	public Template() {
		config = Configuration.getDefaultConfig();
		init();
	}
	
	private void init() {
		factory = new JangodEngineFactory();
		loader = new TemplateLoader();
		loader.setBase(config.getTemplateRoot());
		loader.setEncoding(config.getEncoding());
		setCommonBindings(new SimpleBindings());
	}
	
	public void setCommonBindings(Bindings bindings) {
		bindings.put(Configuration.CONFIG_VAR, config);
		factory.setGlobalBindings(bindings);
	}
	
	public Bindings createBindings(int type) {
		switch (type) {
		case CACHEBINDINGS :
			return new CacheBindings();
		case LAZYBINDINGS :
			return new LazyBindings();
		default :
			return new SimpleBindings();
		}
	}
	
	public String render(String tpl, Bindings engineBindings) throws TemplateException{
		return render(tpl, engineBindings, config.getEncoding());
	}
	
	public String render(String tpl, Bindings engineBindings, String encoding) throws TemplateException {
		ScriptEngine engine = factory.getScriptEngine();
		try {
			return (String) engine.eval(loader.getReader(tpl, encoding), engineBindings);
		} catch (ScriptException e) {
			throw new TemplateException(e.getMessage());
		} catch (IOException e) {
			throw new TemplateException(e.getMessage());
		}
	}
	
	public void render(String tpl, Bindings engineBindings, Writer out) throws TemplateException, IOException{
		out.write(render(tpl, engineBindings));
	}
	
	public void render(String tpl, Bindings engineBindings, String encoding, Writer out) throws TemplateException, IOException{
		out.write(render(tpl, engineBindings, encoding));
	}
	
	
	public String render(String tpl) throws TemplateException{
		return render(tpl, config.getEncoding());
	}
	
	public String render(String tpl, String encoding) throws TemplateException {
		ScriptEngine engine = factory.getScriptEngine();
		try {
			return (String) engine.eval(loader.getReader(tpl, encoding));
		} catch (ScriptException e) {
			throw new TemplateException(e.getMessage());
		} catch (IOException e) {
			throw new TemplateException(e.getMessage());
		}
	}
	
	public void render(String tpl, Writer out) throws TemplateException, IOException{
		out.write(render(tpl));
	}
	
	public void render(String tpl, String encoding, Writer out) throws TemplateException, IOException{
		out.write(render(tpl, encoding));
	}
}
