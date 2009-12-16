package org.springframework.web.servlet.view.jangod;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.util.WebUtils;

import net.asfun.template.Configuration;
import net.asfun.template.Template;

public class JangodConfigurer implements JangodConfig, InitializingBean, ResourceLoaderAware, ServletContextAware {

	private Template template;
	private ServletContext servletContext;
	private String encoding;
	private String root = File.separator;
	private boolean useTheme = false;
	private String webRoot;

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
			webRoot = WebUtils.getRealPath(servletContext, "/");
			if ( ! webRoot.endsWith(File.separator) ) {
				webRoot += File.separator;
			}
			if ( encoding != null ) {
				config.setEncoding(encoding);
			}
			template = new Template(config);
			setTemplateRoot();
		}
	}
	
	private void setTemplateRoot() {
		if ( root.startsWith(File.separator) ) {
			template.getConfiguration().setTemplateRoot(webRoot + root.substring(1));
		} else {
			template.getConfiguration().setTemplateRoot(webRoot + root);
		}
	}

	public String getRoot() {
		return root;
	}

	@Override
	public void setRoot(String root) {
		if ( root != null && root.trim().length() > 0 ) {
			if ( ! root.endsWith(File.separator) ) {
				root += File.separator;
			}
			this.root = root;
			if ( template != null ) {
				setTemplateRoot();
			}
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
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

	public String getEncoding() {
		return encoding;
	}

}
