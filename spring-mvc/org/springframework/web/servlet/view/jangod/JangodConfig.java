package org.springframework.web.servlet.view.jangod;

import net.asfun.template.Template;

public interface JangodConfig {

	public Template getTemplate();
	
	public boolean isUseTheme();
	
	public void setRoot(String root);
}
