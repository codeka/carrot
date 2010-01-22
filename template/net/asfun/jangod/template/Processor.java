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
package net.asfun.jangod.template;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.asfun.jangod.base.Configuration;
import net.asfun.jangod.base.Context;
import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.parse.JangodParser;

/**
 * DON'T run in multi-thread
 * @author anysome
 *
 */
public class Processor {

	protected Context context;
	JangodParser parser;
	JangodInterpreter interpreter;
	boolean hasRoot = false;
	
	public Processor(TemplateConfig config) {
		context = new Context();
		if ( config != null ) {
			if ( config.getTemplateEncoding() != null ) {
				context.getConfiguration().setEncoding(config.getTemplateEncoding());
			}
			if ( config.getTemplateRoot() != null ) {
				context.getConfiguration().setWorkspace(config.getTemplateRoot());
				hasRoot = true;
			}
		}
		parser = new JangodParser("");
		interpreter = new JangodInterpreter(context);
	}
	
	private String getFullFileName(String fileName) {
		if ( hasRoot ) {
			return context.getConfiguration().getWorkspace() + fileName;
		}
		return fileName;
	}
	
	public Configuration getConfiguration() {
		return context.getConfiguration();
	}
	
	public void setCommonBindings(Map<String, Object> bindings) {
		if ( bindings == null ) {
			context.reset(Context.SCOPE_GLOBAL);
		} else {
			context.initBindings(bindings, Context.SCOPE_GLOBAL);
		}
	}
	
	public String render(String templateFile, Map<String, Object> bindings) throws IOException {
		return render(templateFile, bindings, context.getConfiguration().getEncoding());
	}
	
	public String render(String templateFile, Map<String, Object> bindings, String encoding) throws IOException {
		if ( bindings == null ) {
			context.reset(Context.SCOPE_SESSION);
		} else {
			context.initBindings(bindings, Context.SCOPE_SESSION);
		}
		try {
			parser.init(ResourceManager.getResource(getFullFileName(templateFile), encoding));
			interpreter.init();
			return interpreter.render(parser);
		} catch ( Exception e) {
			throw new IOException(e.getMessage(), e.getCause());
		}
	}
	
	public void render(String templateFile, Map<String, Object> bindings, Writer out, String encoding) throws IOException {
		out.write( render(templateFile, bindings, encoding) );
	}
	
	public void render(String templateFile, Map<String, Object> bindings, Writer out) throws IOException {
		out.write( render(templateFile, bindings) );
	}
}
