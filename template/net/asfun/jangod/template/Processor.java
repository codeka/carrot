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
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.base.Configuration;
import net.asfun.jangod.base.Context;
import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.interpret.JangodInterpreter;

/**
 * Processor for processing a template. Can only be used once and must be re-created each time.
 */
public class Processor {

	protected Context context;
	protected Application application;
	JangodInterpreter interpreter;

	private boolean used;
	
	public Processor(Application application) {
		this.application = application;
		context = new Context(application);
		interpreter = new JangodInterpreter(context);
	}
	
	public Configuration getConfiguration() {
		return context.getConfiguration();
	}

	public String render(String templateFile, Map<String, Object> bindings) throws IOException {
		return render(templateFile, bindings, context.getConfiguration().getEncoding());
	}

	public String render(String templateFile, Map<String, Object> bindings, String encoding)
			throws IOException {
		StringWriter writer = new StringWriter();
		render(templateFile, bindings, writer, encoding);
		return writer.getBuffer().toString();
	}

	public void render(String templateFile, Map<String, Object> bindings, Writer writer)
			throws IOException {
		render(templateFile, bindings, writer, context.getConfiguration().getEncoding());
	}

	public void render(String templateFile, Map<String, Object> bindings, Writer writer,
			String encoding) throws IOException {
		if (used) {
			throw new IllegalStateException("Cannot use Processor more than once.");
		}
		used = true;

		context.initBindings(bindings, Context.SCOPE_SESSION);
		String fullName = ResourceManager.getFullName(
				templateFile, application.getConfiguration().getWorkspace());
		interpreter.setFile(fullName);
		try {
			interpreter.init();
			interpreter.render(application.getParseResult(fullName, encoding), writer);
		} catch (Exception e) {
			throw new IOException(e.getMessage(), e.getCause());
		}
	}
}
