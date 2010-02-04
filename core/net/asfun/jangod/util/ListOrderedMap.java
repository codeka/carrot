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
package net.asfun.jangod.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.asfun.jangod.base.Constants;

public class ListOrderedMap implements Map<String, Object>, Iterable<ListOrderedMap.Item>{

	private HashMap<String, Object> store = new HashMap<String, Object>();
	private ArrayList<String> keys = new ArrayList<String>();
	
	@Override
	public void clear() {
		keys.clear();
		store.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return store.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return store.containsValue(value);
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		return store.entrySet();
	}

	@Override
	public Object get(Object key) {
		return store.get(key);
	}

	@Override
	public boolean isEmpty() {
		return keys.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return store.keySet();
	}

	@Override
	public Object put(String key, Object value) {
		if (key == null) {
            throw new NullPointerException("key can not be null");
        }
        if (!(key instanceof String)) {
            throw new ClassCastException("key should be a String");
        }
        if (key.equals(Constants.STR_BLANK)) {
            throw new IllegalArgumentException("key can not be empty");
        }
        keys.add(key);
		return store.put(key, value);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(Object key) {
		int index = keys.indexOf(key);
		if ( index != -1 ) {
			keys.remove(index);
			return store.remove(key);
		}
		return null;
	}

	@Override
	public int size() {
		return keys.size();
	}

	@Override
	public Collection<Object> values() {
		return store.values();
	}

	@Override
	public Iterator<ListOrderedMap.Item> iterator() {
		ArrayList<ListOrderedMap.Item> res = new ArrayList<ListOrderedMap.Item>(keys.size());
		for(String key : keys) {
			res.add(new ListOrderedMap.Item(key, store.get(key)));
		}
		return res.iterator();
	}

	public static class Item {
		
		private String k;
		private Object v;
		
		private Item(String key, Object value) {
			k = key;
			v = value;
		}
		
		public String getKey() {
			return k;
		}
		
		public Object getValue() {
			return v;
		}
	}

}
