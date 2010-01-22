package net.asfun.jangod.cache;

import java.io.Serializable;

public class NoopStorage<K,V extends Serializable> implements StatelessObjectStorage<K, V> {

	@Override
	public void clear() {
		
	}

	@Override
	public V get(K key) {
		return null;
	}

	@Override
	public void put(K key, V value) {
		
	}

	@Override
	public void remove(K key) {
		
	}

}
