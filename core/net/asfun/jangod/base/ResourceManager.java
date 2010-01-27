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

import java.io.IOException;

import net.asfun.jangod.cache.NoopStorage;
import net.asfun.jangod.cache.StatelessObjectStorage;
import net.asfun.jangod.cache.SynchronousStorage;


public class ResourceManager {

	static ResourceLocater locater;
	static StatelessObjectStorage<String, String> cache;
	
	static {
		init();
	}
	
	@SuppressWarnings("unchecked")
	private static void init() {
		Configuration config = Configuration.config;
		String locaterClass = config.getProperty("file.locater", "net.asfun.jangod.base.FileLocater");
		try {
			locater = (ResourceLocater) Class.forName(locaterClass).newInstance();
		} catch (Exception e) {
			locater = new FileLocater();
			JangodLogger.warning("Can't instance file loader(use default) >>> " + locaterClass);
		}
		
		String storeClass = config.getProperty("file.cache");
		if ( storeClass == null ) {
			cache = new NoopStorage<String,String>();
		} else {
			try {
				cache = (StatelessObjectStorage<String, String>) Class.forName(storeClass).newInstance();
			} catch (Exception e) {
				cache = new SynchronousStorage<String,String>();
				JangodLogger.warning("Can't instance file cacher(use default) >>> " + storeClass);
			}
		}
	}
	
	public static String getResource(String file, String encoding) throws IOException {
		String key = file + "@" + encoding;
		String value = cache.get(key);
		if ( value == null ) {
			value = locater.getString(file, encoding);
			cache.put(key, value);
		}
		return value;
	}
	
	public static String getFullName(String relativeName, String relativeDir, String defaultDir) throws IOException{
		return locater.getFullName(relativeName, relativeDir, defaultDir);
	}
	
	public static String getFullName(String relativeName, String defaultDir) throws IOException{
		return locater.getFullName(relativeName, defaultDir);
	}
	
	public static String getDirectory(String fullName) throws IOException{
		return locater.getDirectory(fullName);
	}
}
