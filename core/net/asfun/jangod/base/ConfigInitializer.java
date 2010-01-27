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
package net.asfun.jangod.base;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import net.asfun.jangod.lib.Importable;

public class ConfigInitializer {

	final static String AUTOCONFIG_FILE = "jangod.config.properties";
	final static String CONFIG_FILE_PROPERTY = "jangod.configurationFile";

	private static URL findConfigFile(String configurationFile) {
		String file = null;
		if ( configurationFile == null ) {
			try {
				file = System.getProperty(CONFIG_FILE_PROPERTY);
				if (file == null) {
					file = System.getenv(CONFIG_FILE_PROPERTY);
				}
			} catch (SecurityException e) {
				file = null;
				JangodLogger.finest("Can't get file name from env by key >>> " + CONFIG_FILE_PROPERTY);
			}
			if (file == null) {
				file = AUTOCONFIG_FILE;
			}
		} else {
			file = configurationFile;
		}
		URL result = null;
		try {
			result = new URL(file);
		} catch (MalformedURLException e) {
			result = Configuration.class.getClassLoader().getResource(file);
		}
		if (result == null) {
			File f = new File(file);
			if (f.exists() && f.isFile()) {
				try {
					result = f.toURI().toURL();
				} catch (MalformedURLException e) {
					JangodLogger.warning("Can't find file >>> " + file);
				}
			} else {
				JangodLogger.warning("Can't find config file >>> " + file);
			}
		}
		return result;
	}

	static Configuration getConfig(String configurationFile) {
		Configuration config = new Configuration();
		URL u = findConfigFile(configurationFile);
		if (u != null) {
			InputStream is = null;
			try {
				is = u.openStream();
				Properties properties = new Properties();
				properties.load(is);
				config.properties = properties;
				if (properties.containsKey("lib.imports")) {
					String exts = properties.getProperty("lib.imports");
					String[] imports = exts.split("\\s+");
					for(String importClass : imports) {
						try {
							Importable importee = (Importable) Class.forName(importClass).newInstance();
							Configuration.addImport(importee);
						} catch (Exception e) {
							JangodLogger.warning("Can't created importable object >>> " + importClass);
						}
					}
				}
				JangodLogger.fine("Load config from file >>> " + u.getFile());
				if (properties.containsKey("language")) {
					try {
						config.setLocale(new Locale(properties.getProperty("language")));
					} catch(Exception e) {
						config.setLocale(Locale.CHINESE);
						JangodLogger.warning("Language value from config file is invalid(use default) >>> "
								+ properties.getProperty("language"));
					}
				} else {
					config.setLocale(Locale.CHINESE);
				}
				if (properties.containsKey("timezone")) {
					try {
						config.setTimezone(TimeZone.getTimeZone(properties.getProperty("timezone")));
					} catch(Exception e) {
						config.setTimezone(TimeZone.getDefault());
						JangodLogger.warning("Timezone value from config file is invalid(use default) >>> "
								+ properties.getProperty("timezone"));
					}
				} else {
					config.setTimezone(TimeZone.getDefault());
				}
				//TODO check encoding value is valid
				if (properties.containsKey("file.encoding")) {
					config.setEncoding(properties.getProperty("file.encoding"));
				} else if ( System.getProperty("file.encoding") != null) {
					config.setEncoding(System.getProperty("file.encoding"));
				} else {
					config.setEncoding("utf-8");
				}
				if (properties.containsKey("file.root")) {
					config.setWorkspace(properties.getProperty("file.root"));
				}
			} catch (IOException e) {
				JangodLogger.warning("Reading configuration file error >>> " + e.getMessage());
			} finally {
				if ( is != null ) {
					try {
						is.close();
					} catch (IOException e) {
						JangodLogger.warning("Reading configuration file error >>> " + e.getMessage());
					}
				}
			}
		}
		return config;
	}

}
