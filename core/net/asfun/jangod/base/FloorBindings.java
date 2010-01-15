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

import java.util.HashMap;
import java.util.Map;

public class FloorBindings implements Cloneable{

	private Map<Integer, Map<String,Object>> floor;
	
	public FloorBindings() {
		Map<String,Object> root = new HashMap<String,Object>();
		floor = new HashMap<Integer, Map<String,Object>>();
		floor.put(1, root);
	}
	
	public Object put(String name, Object value, int level) {
		checkKey(name);
		return getBindings(level).put(name, value);
	}
	
	public void putAll(Map<? extends String, ? extends Object> toMerge, int level) {
		if (toMerge == null) {
            throw new NullPointerException("toMerge map is null");
        }
		for (Map.Entry<? extends String, ? extends Object> entry : toMerge.entrySet()) {
			put(entry.getKey(), entry.getValue(), level);
		}
	}
	
	public boolean containsKey(String key, int level) {
		return getBindings(level).containsKey(key);
	}
	
	public Object get(String key, int level) {
		checkKey(key);
		return getBindings(level).get(key);
	}
	
	public Object remove(Object key, int level) {
		return getBindings(level).remove(key);
	}
	
	public void clear(int level) {
		getBindings(level).clear();
	}
	
	public boolean containsValue(Object value, int level) {
		return getBindings(level).containsValue(value);
	}
	
	private Map<String, Object> getBindings(int level) {
		Map<String,Object> map = floor.get(level);
		if ( map == null ) {
			map = new HashMap<String,Object>();
			floor.put(level, map);
		}
		return map;
	}
	
	private void checkKey(String key) {
        if (key == null) {
            throw new NullPointerException("key can not be null");
        }
        if (key.equals("")) {
            throw new IllegalArgumentException("key can not be empty");
        }
    }
	
	@Override
	public FloorBindings clone() {
		FloorBindings fb = new FloorBindings();
		for (Map.Entry<Integer, Map<String,Object>> entry : floor.entrySet()) {
			fb.putAll(entry.getValue(), entry.getKey());
		}
		return fb;
	}
}
