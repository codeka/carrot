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
package net.asfun.jangod.template;

public class SimpleConfig implements TemplateConfig {

	String encoding;
	String root;
	
	@Override
	public String getTemplateEncoding() {
		return encoding;
	}

	@Override
	public String getTemplateRoot() {
		return root;
	}
	
	public void setTemplateEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void setTemplateRoot(String root) {
		this.root = root;
	}

}
