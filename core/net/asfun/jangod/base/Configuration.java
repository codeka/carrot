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
package net.asfun.jangod.base;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.File;
import java.util.Locale;
import java.util.TimeZone;

import net.asfun.jangod.cache.SynchronousStorage;
import net.asfun.jangod.lib.Filter;
import net.asfun.jangod.lib.FilterLibrary;
import net.asfun.jangod.lib.Importable;
import net.asfun.jangod.lib.Macro;
import net.asfun.jangod.lib.MacroLibrary;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.lib.TagLibrary;

public class Configuration {
	private String encoding;
	private Locale locale;
	private TimeZone timezone;
	private String workspace;
	private ResourceLocater resourceLocater;
	private Class<?> parseCacheClass;

	protected Configuration() {
		resourceLocater = new FileLocater();
		parseCacheClass = SynchronousStorage.class;
	};
	
	public static void addImport(Importable importee) {
		if ( importee instanceof Filter) {
			FilterLibrary.addFilter((Filter)importee);
		}
		else if ( importee instanceof Tag) {
			TagLibrary.addTag((Tag)importee);
		}
		else if ( importee instanceof Macro ) {
			MacroLibrary.addMacro((Macro)importee);
		} else {
			if ( importee != null )
				JangodLogger.warning("Can't recognize the importing object >>> " + importee.getClass().getName());
			else 
				JangodLogger.warning("Can't import null object");
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

	public String getWorkspace() {
		return workspace;
	}
	
	public void setWorkspace(String rootPath) {
		if ( rootPath == null) return;
		if ( rootPath.endsWith(File.separator) ) {
			workspace = rootPath.substring(0, rootPath.lastIndexOf(File.separator));
		} else {
			workspace = rootPath;
		}
	}

	public void setResourceLocater(ResourceLocater resourceLocater) {
		this.resourceLocater = resourceLocater;
	}

	public ResourceLocater getResourceLocater() {
		return resourceLocater;
	}

	public void setParseCacheClass(Class<?> clazz) {
		parseCacheClass = clazz;
	}

	public Class<?> getParseCacheClass() {
		return parseCacheClass;
	}
}