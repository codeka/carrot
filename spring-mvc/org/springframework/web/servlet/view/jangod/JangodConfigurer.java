package org.springframework.web.servlet.view.jangod;

import java.io.File;
import java.util.TimeZone;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;

import net.asfun.template.Configuration;
import net.asfun.template.Template;

public class JangodConfigurer implements JangodConfig, InitializingBean, ResourceLoaderAware {

	private Template template;
	private String encoding;
	private String root = File.separator;
	private boolean useTheme = false;
	private String timezone;

	@Override
	public boolean isUseTheme() {
		return useTheme;
	}

	public void setUseTheme(boolean useTheme) {
		this.useTheme = useTheme;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@Override
	public Template getTemplate() {
		return this.template;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if ( template == null ) {
			Configuration config = Configuration.getDefaultConfig();
			if ( encoding != null ) {
				config.setEncoding(encoding);
			}
			if ( root != null ) {
				config.setTemplateRoot(root);
			}
			if ( timezone != null ) {
				config.setTimezone(TimeZone.getTimeZone(timezone));
			}
			template = new Template(config);
		}
	}

	public String getRoot() {
		return root;
	}

	@Override
	public void setRoot(String root) {
		if ( root != null && root.trim().length() > 0 ) {
			this.root = root;
			if ( template != null ) {
				template.getConfiguration().setTemplateRoot(root);
			}
		}
	}

	@Override
	public void setResourceLoader(ResourceLoader arg0) {
		
	}

	public void setEncoding(String encoding) {
		if ( encoding != null && encoding.trim().length() > 0 ) {
			this.encoding = encoding;
			if ( template != null ) {
				template.getConfiguration().setEncoding(encoding);
			}
		}
	}
	
	public void setTimezone(String timezone) {
		if ( timezone != null && timezone.trim().length() > 0 ) {
			this.timezone = timezone;
			if ( template != null ) {
				template.getConfiguration().setTimezone(TimeZone.getTimeZone(timezone));
			}
		}
	}

	public String getEncoding() {
		return encoding;
	}

}
