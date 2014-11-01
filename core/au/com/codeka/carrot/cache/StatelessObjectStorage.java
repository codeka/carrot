package au.com.codeka.carrot.cache;


public interface StatelessObjectStorage<K,V> {
  public V get(K key);
  public void put(K key, V value);
  public void remove(K key);
  public void clear();
}
