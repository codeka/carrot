package au.com.codeka.carrot.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class SynchronousStorage<K, V> implements StatelessObjectStorage<K, V> {

  final HashMap<K, SoftReference<V>> storage = new HashMap<K, SoftReference<V>>();

  @Override
  public void clear() {
    synchronized (storage) {
      storage.clear();
    }
  }

  @Override
  public V get(K key) {
    SoftReference<V> ref = storage.get(key);
    if (ref != null) {
      if (ref.get() == null) {
        remove(key);
      } else {
        return ref.get();
      }
    }
    return null;
  }

  @Override
  public void put(K key, V value) {
    synchronized (storage) {
      storage.put(key, new SoftReference<V>(value));
    }
  }

  @Override
  public void remove(K key) {
    synchronized (storage) {
      SoftReference<V> value = storage.remove(key);
      value.enqueue();
      value = null;
    }
  }

}
