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

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import net.asfun.jangod.base.ConfigInitializer;
import net.asfun.jangod.cache.ConcurrentListPool;
import net.asfun.jangod.cache.StatefulObjectPool;

public class TemplateEngine {

	Map<String, Object> commonBindings = new HashMap<String, Object>(0);
	StatefulObjectPool<Processor> pool;
	TemplateConfig config = null;
	
	public TemplateEngine() {
		initProcessorPool();
	}
	
	public TemplateEngine(TemplateConfig config) {
		this.config = config;
		initProcessorPool();
	}
	
	@SuppressWarnings("unchecked")
	protected void initProcessorPool() {
		String poolClass = ConfigInitializer.getProperty("processor.pool");
		if ( poolClass == null ) {
			pool = new ConcurrentListPool<Processor>();
		} else {
			try {
				pool = (StatefulObjectPool<Processor>) Class.forName(poolClass).newInstance();
			} catch(Exception e) {
				pool = new ConcurrentListPool<Processor>();
				JangodLogger.warning("Can't instance processor pool(use default) >>> " + poolClass);
			}
		}
	}
	
	public void setEngineBindings(Map<String, Object> bindings ) {
		if (bindings == null ) {
			commonBindings.clear();
		} else {
			commonBindings = bindings;
		}
	}
	
	public void setEngineConfig(TemplateConfig config) {
		this.config = config;
	}
	
	public String process(String templateFile, Map<String, Object> bindings, TemplateConfig config) throws IOException {
		Processor processor = new Processor(config);
		processor.setCommonBindings(commonBindings);
		return processor.render(templateFile, bindings);
	}
	
	public String process(String templateFile, Map<String, Object> bindings) throws IOException {
		Processor processor = pool.pop();
		if ( processor == null ) {
			processor = new Processor(config);
		}
		processor.setCommonBindings(commonBindings);
		String result = processor.render(templateFile, bindings);
		pool.push(processor);
		return result;
	}
	
	public void process(String templateFile, Map<String, Object> bindings, Writer out, TemplateConfig config) throws IOException {
		Processor processor = new Processor(config);
		processor.setCommonBindings(commonBindings);
		processor.render(templateFile, bindings, out);
	}
	
	public void process(String templateFile, Map<String, Object> bindings, Writer out) throws IOException {
		Processor processor = pool.pop();
		if ( processor == null ) {
			processor = new Processor(config);
		}
		processor.setCommonBindings(commonBindings);
		processor.render(templateFile, bindings, out);
		pool.push(processor);
	}
}
