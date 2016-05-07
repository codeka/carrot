package au.com.codeka.carrot.template;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptException;

import au.com.codeka.carrot.base.CarrotException;

public class LazyBindings implements Map<String, Object> {

  private Map<String, Object> bins;
  private Set<String> keys;
  private Map<String, String> lazies;
  private Map<String, Method> methods;

  public LazyBindings() {
    bins = new HashMap<String, Object>();
    keys = new HashSet<String>();
    lazies = new HashMap<String, String>();
    methods = new HashMap<String, Method>();
  }

  /**
   * set up lazy load bindings
   * 
   * @param props
   *          like recentPosts => AutoLoader.getPosts
   * @throws ScriptException
   */
  public void config(Map<String, String> props) {
    lazies = props;
    keys.addAll(props.keySet());
    bins.clear();
  }

  @Override
  public boolean containsKey(Object key) {
    return keys.contains(key);
  }

  @Override
  public Object get(Object key) {
    checkKey(key);
    if (keys.contains(key)) {
      Object value = bins.get(key);
      if (value == null) {
        try {
          value = getFromDataSource(key);
        } catch (CarrotException e) {
          // TODO??
        }
        // bins.put(key.toString(), value);
      }
      return value;
    }
    return null;
  }

  private Object getFromDataSource(Object key) throws CarrotException {
    try {
      String lazy = lazies.get(key);
      if (!methods.containsKey(lazy)) {
        int sep = lazy.lastIndexOf('.');
        Class<?> c = Class.forName(lazy.substring(0, sep));
        Method method = c.getDeclaredMethod(lazy.substring(sep + 1), new Class[] {});
        methods.put(lazy, method);
      }
      return methods.get(lazy).invoke(null, new Object[] {});
    } catch (Exception e) {
      throw new CarrotException(e);
    }
  }

  @Override
  public Object put(String name, Object value) {
    checkKey(name);
    keys.add(name);
    return bins.put(name, value);
  }

  @Override
  public void putAll(Map<? extends String, ? extends Object> toMerge) {
    if (toMerge == null) {
      throw new NullPointerException("toMerge map is null");
    }
    for (Map.Entry<? extends String, ? extends Object> entry : toMerge.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public Object remove(Object key) {
    keys.remove(key);
    if (bins.containsKey(key)) {
      lazies.remove(key);
      return bins.remove(key);
    } else {
      try {
        Object value = getFromDataSource(key);
        lazies.remove(key);
        return value;
      } catch(CarrotException e) {
        return null;
      }
    }
  }

  @Override
  public void clear() {
    keys.clear();
    bins.clear();
    lazies.clear();
  }

  @Override
  public boolean containsValue(Object value) {
    return values().contains(value);
  }

  @Override
  public Set<java.util.Map.Entry<String, Object>> entrySet() {
    return bins.entrySet();
  }

  @Override
  public boolean isEmpty() {
    return keys.isEmpty();
  }

  @Override
  public Set<String> keySet() {
    return keys;
  }

  @Override
  public int size() {
    return keys.size();
  }

  @Override
  public Collection<Object> values() {
    for (String key : lazies.keySet()) {
      try {
        bins.put(key, getFromDataSource(key));
      } catch (CarrotException e) {
        // TODO??
      }
    }
    return bins.values();
  }

  private void checkKey(Object key) {
    if (key == null) {
      throw new NullPointerException("key can not be null");
    }
    if (!(key instanceof String)) {
      throw new ClassCastException("key should be a String");
    }
    if (key.equals("")) {
      throw new IllegalArgumentException("key can not be empty");
    }
  }

}
