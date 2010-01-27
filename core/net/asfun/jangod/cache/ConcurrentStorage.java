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
package net.asfun.jangod.cache;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentStorage<K,V> implements StatelessObjectStorage<K,V> {

	final ConcurrentHashMap<K, SoftReference<V>> storage = new ConcurrentHashMap<K, SoftReference<V>>();
	
	@Override
	public void clear() {
		storage.clear();
	}

	@Override
	public V get(K key) {
		SoftReference<V> ref = storage.get(key);
		if ( ref != null ) {
			if ( ref.get() == null ) {
				storage.remove(key);
			} else {
				return ref.get();
			}
		}
		return null;
	}

	@Override
	public void put(K key, V value) {
		storage.put(key, new SoftReference<V>(value));
	}

	@Override
	public void remove(K key) {
		storage.remove(key);
	}


}
