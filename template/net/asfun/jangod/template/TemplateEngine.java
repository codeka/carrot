package net.asfun.jangod.template;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TemplateEngine {

	Map<String, Object> commonBindings = new HashMap<String, Object>(0);
	TemplateConfig config;
	
	public TemplateEngine() {
		config = new SimpleConfig();
	}
	
	public TemplateEngine(TemplateConfig config) {
		this.config = config;
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
		Processor processor = new Processor(config);
		processor.setCommonBindings(commonBindings);
		return processor.render(templateFile, bindings);
	}
	
	public void process(String templateFile, Map<String, Object> bindings, Writer out, TemplateConfig config) throws IOException {
		Processor processor = new Processor(config);
		processor.setCommonBindings(commonBindings);
		processor.render(templateFile, bindings, out);
	}
	
	public void process(String templateFile, Map<String, Object> bindings, Writer out) throws IOException {
		Processor processor = new Processor(config);
		processor.setCommonBindings(commonBindings);
		processor.render(templateFile, bindings, out);
	}
}
