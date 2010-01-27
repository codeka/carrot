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
import java.util.Hashtable;
import java.util.Map;

import net.asfun.jangod.cache.StatelessObjectStorage;
import net.asfun.jangod.cache.SynchronousStorage;

public class Application {

	Map<String, Object> globalBindings = new Hashtable<String, Object>(5);
	Configuration config;
	ResourceLoader loader;
	StatelessObjectStorage<String, String> resourceCache;
	
	
	public Application() {
		config = Configuration.getDefault().clone();
		init();
	}
	
	public Application(String configFile) {
		config = ConfigInitializer.getConfig(configFile);
		init();
	}
	
	public Map<String, Object> getGlobalBindings() {
		return globalBindings;
	}

	public void setGlobalBindings(Map<String, Object> globalBindings) {
		this.globalBindings = globalBindings;
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		//file loader
		String loaderClass = config.getProperty("file.loader", "net.asfun.jangod.base.FileLoader");
		try {
			loader = (ResourceLoader) Class.forName(loaderClass).newInstance();
		} catch (Exception e) {
			loader = new FileLoader();
			JangodLogger.warning("Can't instance file loader(use default) >>> " + loaderClass);
		}
		loader.setConfiguration(config);
		//file cacher
		String storeClass = config.getProperty("file.cacher");
		if ( storeClass == null ) {
			resourceCache = new SynchronousStorage<String,String>();
		} else {
			try {
				resourceCache = (StatelessObjectStorage<String, String>) Class.forName(storeClass).newInstance();
			} catch (Exception e) {
				resourceCache = new SynchronousStorage<String,String>();
				JangodLogger.warning("Can't instance file cacher(use default) >>> " + loaderClass);
			}
		}
	}
	
	public String getResource(String file, String directory) throws IOException {
		return getResource(file, config.getEncoding(), directory);
	}
	
	public String getResource(String file, String encoding, String directory) throws IOException {
		String key;
		if ( directory != null ) {
			key = directory + file;
		} else {
			key = file;
		}
		String value = resourceCache.get(key);
		if ( value == null ) {
			value = loader.getString(file, encoding, directory);
			resourceCache.put(key, value);
		}
		return value;
	}
	
	public String getDirectory(String file) {
		return loader.getDirectory(file, config.getWorkspace());
	}

	public Configuration getConfiguration() {
		return config;
	}
	
}
