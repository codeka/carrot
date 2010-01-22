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
import org.springframework.beans.factory.InitializingBean;
import net.asfun.jangod.template.TemplateEngine;

public class JangodConfigurer implements JangodConfig, InitializingBean{

	String encoding;
	String root;
	boolean useTheme = false;
	TemplateEngine engine;

	@Override
	public void setRoot(String root) {
		if ( root != null && root.trim().length() > 0 ) {
			if ( ! root.endsWith(File.separator) ) {
				this.root = root + File.separator;
			} else {
				this.root = root;
			}
		}
	}

	public void setEncoding(String encoding) {
		if ( encoding != null && encoding.trim().length() > 0 ) {
			this.encoding = encoding;
		}
	}
	
	public void setUseTheme(boolean tof) {
		this.useTheme = tof;
	}

	@Override
	public String getTemplateEncoding() {
		return encoding;
	}

	@Override
	public String getTemplateRoot() {
		return root;
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
		engine = new TemplateEngine(this);
	}

}
