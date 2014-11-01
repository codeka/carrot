package au.com.codeka.carrot.cache;

public class NoopStorage<K, V> implements StatelessObjectStorage<K, V> {
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
