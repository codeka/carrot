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

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * supporting concurrency
 * @author anysome
 *
 * @param <T>
 */
public class ConcurrentHashPool<T> implements StatefulObjectPool<T>{

	final ConcurrentHashMap<Integer, Reference<T>> pool = new ConcurrentHashMap<Integer, Reference<T>>();
	private int minimum = 10;
	private AtomicInteger counter = new AtomicInteger(0);
	
	public ConcurrentHashPool(){}
	
	public ConcurrentHashPool(int size) {
		if ( size < 0 ) {
			throw new IllegalArgumentException("size can not be negative");
		}
		minimum = size;
	}
	
	@Override
	public T pop() {
		Iterator<Integer> keys = pool.keySet().iterator();
		while ( keys.hasNext() ) {
			Reference<T> ref = pool.remove(keys.next());
			if ( ref != null ) {
				if ( ref instanceof SoftReference<?> ) {
					counter.decrementAndGet();
				}
				return ref.get();
			}
		}
		return null;
	}
	
	@Override
	public void push(T instance) {
		if ( counter.intValue() < minimum ) {
			pool.put(instance.hashCode(), new SoftReference<T>(instance));
			counter.incrementAndGet();
		} else {
			pool.put(instance.hashCode(), new WeakReference<T>(instance));
		}
	}
}
