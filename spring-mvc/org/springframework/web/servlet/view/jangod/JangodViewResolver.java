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

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.util.WebUtils;

public class JangodViewResolver extends AbstractTemplateViewResolver {
	
	private JangodConfig jangodConfig;
	protected boolean isConfigUnset = true;
	private ModelDataProvider commonAttributes;
	
	public JangodViewResolver() {
		setViewClass(requiredViewClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Class requiredViewClass() {
		return JangodView.class;
	}
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		JangodView view = (JangodView) super.buildView(viewName);
		if ( isConfigUnset ) {
			try {
				String webRoot = WebUtils.getRealPath(getServletContext(), "/");
				if ( ! webRoot.endsWith(File.separator) ) {
					webRoot += File.separator;
				}
				this.jangodConfig.setRoot(webRoot + getPrefix());
			} catch (Exception e) {
				logger.error("set web root to template engine error.", e.getCause());
			}
			if ( commonAttributes != null ) {
				jangodConfig.getEngine().setEngineBindings(commonAttributes.getModel());
			}
			isConfigUnset = false;
		}
		view.setJangodConfig(jangodConfig);
		view.setUrl(viewName + getSuffix());
		return view;
	}

	public JangodConfig getJangodConfig() {
		return jangodConfig;
	}

	public void setJangodConfig(JangodConfig jangodConfig) {
		this.jangodConfig = jangodConfig;
	}

	public ModelDataProvider getCommonAttributes() {
		return commonAttributes;
	}

	public void setCommonAttributes(ModelDataProvider commonAttributes) {
		this.commonAttributes = commonAttributes;
	}

}
