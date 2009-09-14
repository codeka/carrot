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
package net.asfun.template.bin;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.script.Bindings;

public class CacheBindings implements Bindings{
	
	private Cache cache;
	
	public void config(Map<String, String> props) throws CacheException {
		CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
		cache = cacheFactory.createCache(props);
	}

	@Override
	public boolean containsKey(Object key) {
		return cache.containsKey(key);
	}

	@Override
	public Object get(Object key) {
		checkKey(key);
		return cache.get(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object put(String name, Object value) {
		checkKey(name);
		return cache.put(name, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> toMerge) {
		if (toMerge == null) {
            throw new NullPointerException("toMerge map is null");
        }
		for (Map.Entry<? extends String, ? extends Object> entry : toMerge.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Object remove(Object key) {
		return cache.remove(key);
	}

	@Override
	public void clear() {
		cache.clear();
	}

	@Override
	public boolean containsValue(Object value) {
		return cache.containsValue(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return cache.entrySet();
	}

	@Override
	public boolean isEmpty() {
		return cache.isEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<String> keySet() {
		return cache.keySet();
	}

	@Override
	public int size() {
		return cache.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> values() {
		return cache.values();
	}
	
	private void checkKey(Object key) {
        if (key == null) {
            throw new NullPointerException("key can not be null");
        }
        if (!(key instanceof String)) {
            throw new ClassCastException("key should be a String");
        }
        if (key.equals("")) {
            throw new IllegalArgumentException("key can not be empty");
        }
    }

}
