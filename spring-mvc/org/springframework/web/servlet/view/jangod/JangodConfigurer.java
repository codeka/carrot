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

import org.springframework.beans.factory.InitializingBean;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.template.TemplateEngine;

public class JangodConfigurer implements JangodConfig, InitializingBean{

	boolean useTheme = false;
	String configurationFile;
	String root;
	TemplateEngine engine;
	
	public void setUseTheme(boolean tof) {
		this.useTheme = tof;
	}
	
	public void setConfigurationFile(String configurationFile) {
		this.configurationFile = configurationFile;
	}

	@Override
	public boolean isUseTheme() {
		return useTheme;
	}

	@Override
	public TemplateEngine getEngine() {
		return engine;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if ( configurationFile != null ) {
			Application application = new Application(configurationFile);
			engine = new TemplateEngine(application);
		} else {
			engine = new TemplateEngine();
		}
		if ( root != null ) {
			engine.getConfiguration().setWorkspace(root);
		}
	}

	@Override
	public void setRoot(String root) {
		if ( engine != null ) {
			engine.getConfiguration().setWorkspace(root);
		} else {
			this.root = root;
		}
	}

}
