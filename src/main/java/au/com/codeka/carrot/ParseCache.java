package au.com.codeka.carrot;

import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.tmpl.Node;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class used to cache parsed template files.
 */
public class ParseCache {
  private final Configuration config;
  private final Map<ResourceName, WeakReference<CacheEntry>> cache;

  public ParseCache(Configuration config) {
    this.config = config;
    cache = new HashMap<>();
  }

  public Node getNode(ResourceName resourceName) throws CarrotException {
    WeakReference<CacheEntry> cacheEntryRef = cache.get(resourceName);
    if (cacheEntryRef != null) {
      CacheEntry cacheEntry = cacheEntryRef.get();
      if (cacheEntry != null) {
        long modifiedTime = config.getResourceLocator().getModifiedTime(resourceName);
        if (modifiedTime != cacheEntry.modifiedTime) {
          cache.remove(resourceName);
          return null;
        }

        return cacheEntry.node;
      }
    }

    return null;
  }

  public void addNode(ResourceName resourceName, Node node) throws CarrotException {
    long modifiedTime = config.getResourceLocator().getModifiedTime(resourceName);
    cache.put(resourceName, new WeakReference<>(new CacheEntry(node, modifiedTime)));
  }

  private static class CacheEntry {
    Node node;
    long modifiedTime;

    public CacheEntry(Node node, long modifiedTime) {
      this.node = node;
      this.modifiedTime = modifiedTime;
    }
  }
}
