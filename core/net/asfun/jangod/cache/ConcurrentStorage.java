package net.asfun.jangod.cache;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentStorage<K,V extends Serializable> implements StatelessObjectStorage<K,V> {

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
