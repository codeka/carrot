package org.springframework.web.servlet.view.jangod;

import net.asfun.jangod.template.TemplateConfig;
import net.asfun.jangod.template.TemplateEngine;

public interface JangodConfig extends TemplateConfig{

	public boolean isUseTheme();
	public void setRoot(String root);
	public void setEncoding(String encoding);
	public TemplateEngine getEngine();
	
}
