/**********************************************************************
Copyright (c) 2009 Asfun Net.

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
package net.asfun.template;

import java.io.File;
import java.util.Locale;
import java.util.TimeZone;

import net.asfun.template.compile.Filter;
import net.asfun.template.compile.FilterLibrary;
import net.asfun.template.compile.Importable;
import net.asfun.template.compile.Instruction;
import net.asfun.template.compile.InstructionLibrary;
import net.asfun.template.compile.Tag;
import net.asfun.template.compile.TagLibrary;
import static net.asfun.template.util.logging.JangodLogger;

public class Configuration {
	
	public static final String CONFIG_VAR = "'CFG\"CURRENT";
	
	private String encoding = "utf-8";
	private Locale locale = Locale.CHINESE;
	private TimeZone timezone = TimeZone.getDefault();
	private String root;
	
	protected Configuration(){};
	
	public static void addImport(Importable importee) {
		if ( importee instanceof Filter) {
			FilterLibrary.addFilter((Filter)importee);
		}
		else if ( importee instanceof Tag) {
			TagLibrary.addTag((Tag)importee);
		}
		else if ( importee instanceof Instruction ) {
			InstructionLibrary.addInstruction((Instruction)importee);
		} else {
			if ( importee != null )
				JangodLogger.warning("can't recognize the importing object >>> " + importee.getClass().getName());
			else 
				JangodLogger.warning("can't import null object");
		}
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	public String getEncoding() {
		return encoding;
	}
	
	public Locale getLocale() {
		return locale;
	}
	
	public TimeZone getTimezone() {
		return timezone;
	}

	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}

	public static Configuration getDefaultConfig() {
		return ConfigInitializer.getConfig();
	}

	public String getTemplateRoot() {
		return root;
	}
	
	public void setTemplateRoot(String rootPath) {
		if ( rootPath == null) return;
		if ( ! rootPath.endsWith(File.separator) ) {
			root = rootPath + File.separator;
		} else {
			root = rootPath;
		}
	}
}
