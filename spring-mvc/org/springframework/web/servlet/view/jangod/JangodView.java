package org.springframework.web.servlet.view.jangod;

import java.io.File;
import java.util.Map;

import javax.script.Bindings;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.asfun.template.Template;

import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class JangodView extends AbstractTemplateView{
	
	private Template template;
	private String encoding;
	private JangodConfig jangodConfig;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedTemplateModel(Map model, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		if ( jangodConfig.isUseTheme() ) {
			ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(req);
			String theme = themeResolver.resolveThemeName(req);
			if ( logger.isDebugEnabled() ) {
				logger.debug("Current theme is " + theme);
			}
			template.getConfiguration().setTemplateRoot(jangodConfig.getRoot() + theme + File.separator);
		}
		Bindings bings = template.createBindings(Template.NORMBINDINGS);
		bings.putAll(model);
		if ( encoding == null ) {
			template.render(getUrl(), bings, resp.getWriter());
		} else {
			template.render(getUrl(), bings, encoding, resp.getWriter());
		}
	}
	
	public void setEncoding(String encoding) {
		this.encoding = (encoding!=null ? encoding:"utf-8");
	}
	
	public void setJangodConfig(JangodConfig jangodConfig) {
		this.jangodConfig = jangodConfig;
		template = jangodConfig.getTemplate();
	}
}
