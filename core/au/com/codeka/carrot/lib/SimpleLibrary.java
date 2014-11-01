package au.com.codeka.carrot.lib;

import java.util.HashMap;
import java.util.Map;

public abstract class SimpleLibrary<T> {
  private Map<String, T> lib = new HashMap<String, T>();

  protected SimpleLibrary() {
    initialize();
  }

  protected abstract void initialize();

  public T fetch(String item) {
    String key = item.toLowerCase();
    if (lib.containsKey(key)) {
      return lib.get(key);
    }
    throw new RuntimeException("No filter: " + item);
    // JangodLogger.fine("Library doesn't contain >>> " + item);
    // return null;
  }

  public void register(String item, T obj) {
    lib.put(item.toLowerCase(), obj);
  }

  public boolean isRegistered(String name) {
    return lib.containsKey(name.toLowerCase());
  }
}
