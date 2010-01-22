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
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * supporting concurrency
 * @author anysome
 *
 * @param <T>
 */
public class ConcurrentListPool<T> implements StatefulObjectPool<T>{

	final ConcurrentSkipListMap<Integer, Reference<T>> pool = new ConcurrentSkipListMap<Integer, Reference<T>>();
	private int minimum = 10;
	private AtomicInteger counter = new AtomicInteger(0);
	
	public ConcurrentListPool(){}
	
	public ConcurrentListPool(int size) {
		if ( size < 0 ) {
			throw new IllegalArgumentException("size can not be negative");
		}
		minimum = size;
	}
	
	@Override
	public T pop() {
		Map.Entry<Integer, Reference<T>> entry = pool.pollFirstEntry();
		if ( entry != null ) {
			Reference<T> ref = entry.getValue();
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
