package org.springframework.web.servlet.view.jangod;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class JangodView extends AbstractTemplateView{
	
	protected String encoding;
	protected JangodConfig jangodConfig;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedTemplateModel(Map model, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		JangodConfig config = new JangodConfigurer();
		if ( encoding == null && !jangodConfig.isUseTheme() ) {
			jangodConfig.getEngine().process(getUrl(), model, resp.getWriter());
			return;
		}
		if ( encoding != null ) {
			config.setEncoding(encoding);
		} else {
			config.setEncoding(jangodConfig.getTemplateEncoding());
		}
		if ( jangodConfig.isUseTheme() ) {
			ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(req);
			String theme = themeResolver.resolveThemeName(req);
			if ( logger.isDebugEnabled() ) {
				logger.debug("Current theme is " + theme);
			}
			config.setRoot(jangodConfig.getTemplateRoot() + theme + File.separator);
		} else {
			config.setRoot(jangodConfig.getTemplateRoot());
		}
		jangodConfig.getEngine().process(getUrl(), model, resp.getWriter(), config);
	}
	
	public void setEncoding(String encoding) {
		this.encoding = (encoding!=null ? encoding:"utf-8");
	}
	
	public void setJangodConfig(JangodConfig jangodConfig) {
		this.jangodConfig = jangodConfig;
	}
}
