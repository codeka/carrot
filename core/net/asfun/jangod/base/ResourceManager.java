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

import net.asfun.jangod.cache.StatelessObjectStorage;
import net.asfun.jangod.cache.SynchronousStorage;

@SuppressWarnings("unchecked")
public final class ResourceManager {
	
	static ResourceLoader loader;
	static StatelessObjectStorage<String,String> cache;
	
	static {
		String loaderClass = ConfigInitializer.getProperty("file.loader", "net.asfun.jangod.base.UrlResourceLoader");
		try {
			loader = (ResourceLoader) Class.forName(loaderClass).newInstance();
		} catch (Exception e) {
			loader = new UrlResourceLoader();
			JangodLogger.warning("Can't instance file loader(use default) >>> " + loaderClass);
		}
		loader.setEncoding(ConfigInitializer.getConfig().getEncoding());
		String storeClass = ConfigInitializer.getProperty("file.cacher");
		if ( storeClass == null ) {
			cache = new SynchronousStorage<String,String>();
		} else {
			try {
				cache = (StatelessObjectStorage<String, String>) Class.forName(storeClass).newInstance();
			} catch (Exception e) {
				cache = new SynchronousStorage<String,String>();
				JangodLogger.warning("Can't instance file cacher(use default) >>> " + loaderClass);
			}
		}
	}
	
	private ResourceManager(){}

	public static String getResource(String file) throws IOException {
		String value = cache.get(file);
		if ( value == null ) {
			value = loader.getString(file);
			cache.put(file, value);
		}
		return value;
	}
	
	public static String getResource(String file, String encoding) throws IOException {
		String value = cache.get(file);
		if ( value == null ) {
			value = loader.getString(file, encoding);
			cache.put(file, value);
		}
		return value;
	}
	
}
