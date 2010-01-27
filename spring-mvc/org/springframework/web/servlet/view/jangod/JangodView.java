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
package org.springframework.web.servlet.view.jangod;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

public class JangodView extends AbstractTemplateView{
	
	protected JangodConfig jangodConfig;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedTemplateModel(Map model, HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		if ( ! jangodConfig.isUseTheme() ) {
			jangodConfig.getEngine().process(getUrl(), model, resp.getWriter());
			return;
		} else {
			ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(req);
			String theme = themeResolver.resolveThemeName(req);
			if ( logger.isDebugEnabled() ) {
				logger.debug("Current theme is " + theme);
			}
			jangodConfig.getEngine().process( theme + File.separator + getUrl(),
					model, resp.getWriter());
		} 
	}
	
	public void setJangodConfig(JangodConfig jangodConfig) {
		this.jangodConfig = jangodConfig;
	}
}
