package au.com.codeka.carrot.lib;

import java.util.HashMap;
import java.util.Map;

import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.util.Log;

/** Represents a library of extensions (tags, filters and macros). */
public abstract class Library<T> {
  private Map<String, T> lib = new HashMap<String, T>();
  private Log log;
  private final String kind;

  protected Library(Configuration config, String kind) {
    this.kind = kind;
    log = new Log(config);
    initialize();
  }

  /** Initialize the library and register default objects. */
  protected abstract void initialize();

  public T fetch(String item) {
    String key = item.toLowerCase();
    if (lib.containsKey(key)) {
      return lib.get(key);
    }
    throw new RuntimeException("No " + kind + ": " + item);
  }

  public void register(String name, T obj) {
    lib.put(name.toLowerCase(), obj);
    log.debug("Registered %s: %s -> %s", kind, name.toLowerCase(), obj.getClass().getName());
  }

  public boolean isRegistered(String name) {
    return lib.containsKey(name.toLowerCase());
  }
}
