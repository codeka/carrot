package au.com.codeka.carrot.tree;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TokenParser;
import au.com.codeka.carrot.resource.ResourceName;
import au.com.codeka.carrot.util.Log;

/** Keeps a cache of parse tree for resources, and creates new parse trees on demand. */
public class ParseResultManager {
  private final ConcurrentHashMap<ResourceName, NodeContainer> cache;
  private final Application application;
  private final Log log;

  public ParseResultManager(Application application) {
    this.application = application;

    if (application.getConfiguration().isParseCacheEnabled()) {
      cache = new ConcurrentHashMap<ResourceName, NodeContainer>();
    } else {
      cache = null;
    }

    log = new Log(application.getConfiguration());
  }

  public Node getParseResult(ResourceName resourceName)
      throws IOException, ParseException {
    if (cache == null) {
      return createNode(resourceName);
    }

    Node root = null;
    NodeContainer container = cache.get(resourceName);
    long modifiedTime = application.getConfiguration().getResourceLocater()
        .getModifiedTime(resourceName);
    if (container != null) {
      root = container.node.get();
      if (root != null) {
        // reset to null if the resource has been modified anyway
        if (container.lastModifiedTime != modifiedTime) {
          log.info("Resource has been modified, reloading: %s", resourceName.getName());
          root = null;
        }
      } else {
        log.info("Resource has been garbage-collected, reloading: %s", resourceName.getName());
      }
    } else {
      log.info("Resource not cached, loading: %s", resourceName.getName());
    }
    if (root == null) {
      root = createNode(resourceName);
      cache.put(resourceName, new NodeContainer(root, modifiedTime));
    }

    return root;
  }

  private Node createNode(ResourceName resourceName) throws IOException, ParseException {
    Node root = new TreeParser(application).parse(new TokenParser(
        application.getConfiguration().getResourceLocater().getString(resourceName)));
    root = new TreeRebuilder(application, resourceName).refactor(root);
    return root;
  }

  private static class NodeContainer {
    public SoftReference<Node> node;
    public long lastModifiedTime;

    public NodeContainer(Node node, long lastModifiedTime) {
      this.node = new SoftReference<Node>(node);
      this.lastModifiedTime = lastModifiedTime;
    }
  }
}
