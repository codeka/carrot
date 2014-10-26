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

import net.asfun.jangod.base.Application;
import net.asfun.jangod.base.Configuration;

public class TemplateEngine {
	Application application;
	
	public TemplateEngine() {
		application = new Application();
	}
	
	public TemplateEngine(Application application) {
		this.application = application;
	}
	
	public void setEngineBindings(Map<String, Object> bindings) {
		if (bindings == null ) {
			application.getGlobalBindings().clear();
		} else {
			application.setGlobalBindings(bindings);
		}
	}

	public String process(String templateFile, Map<String, Object> bindings) throws IOException {
		return new Processor(application).render(templateFile, bindings);
	}
	
	public void process(String templateFile, Map<String, Object> bindings, Writer out) throws IOException {
		new Processor(application).render(templateFile, bindings, out);
	}

	public Configuration getConfiguration() {
		return application.getConfiguration();
	}
}
