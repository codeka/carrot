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
	
	public Configuration getConfiguration() {
		return config;
	}
	
	private void init() {
		factory = new JangodEngineFactory();
		loader = new TemplateLoader(config);
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
	
	public String render(String templateFile, Bindings engineBindings) throws TemplateException{
		return render(templateFile, engineBindings, config.getEncoding());
	}
	
	public String render(String templateFile, Bindings engineBindings, String encoding) throws TemplateException {
		ScriptEngine engine = factory.getScriptEngine();
		try {
			return (String) engine.eval(loader.getReader(templateFile, encoding), engineBindings);
		} catch (ScriptException e) {
			throw new TemplateException(e.getMessage());
		} catch (IOException e) {
			throw new TemplateException(e.getMessage());
		}
	}
	
	public void render(String templateFile, Bindings engineBindings, Writer out) throws TemplateException, IOException{
		out.write(render(templateFile, engineBindings));
	}
	
	public void render(String templateFile, Bindings engineBindings, String encoding, Writer out) throws TemplateException, IOException{
		out.write(render(templateFile, engineBindings, encoding));
	}
	
	
	public String render(String templateFile) throws TemplateException{
		return render(templateFile, config.getEncoding());
	}
	
	public String render(String templateFile, String encoding) throws TemplateException {
		ScriptEngine engine = factory.getScriptEngine();
		try {
			return (String) engine.eval(loader.getReader(templateFile, encoding));
		} catch (ScriptException e) {
			throw new TemplateException(e.getMessage());
		} catch (IOException e) {
			throw new TemplateException(e.getMessage());
		}
	}
	
	public void render(String templateFile, Writer out) throws TemplateException, IOException{
		out.write(render(templateFile));
	}
	
	public void render(String templateFile, String encoding, Writer out) throws TemplateException, IOException{
		out.write(render(templateFile, encoding));
	}
}
