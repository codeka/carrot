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

import net.asfun.jangod.template.TemplateConfig;
import net.asfun.jangod.template.TemplateEngine;

public interface JangodConfig extends TemplateConfig{

	public boolean isUseTheme();
	public void setRoot(String root);
	public void setEncoding(String encoding);
	public TemplateEngine getEngine();
	
}
