package au.com.codeka.carrot.tree;

import static au.com.codeka.carrot.util.logging.JangodLogger;

import java.io.IOException;

import au.com.codeka.carrot.base.Application;
import au.com.codeka.carrot.base.Configuration;
import au.com.codeka.carrot.cache.StatelessObjectStorage;
import au.com.codeka.carrot.parse.TokenParser;

public class ParseResultManager {

  StatelessObjectStorage<String, Node> cache;
  Application application;
  static final String join = "@";

  public ParseResultManager(Application application) {
    this.application = application;
    init(application.getConfiguration());
  }

  @SuppressWarnings("unchecked")
  private void init(Configuration config) {
    try {
      cache = (StatelessObjectStorage<String, Node>) config.getParseCacheClass().newInstance();
    } catch (Exception e) {
      JangodLogger.warning(e.toString());
    }
  }

  public Node getParseResult(String file, String encoding) throws IOException {
    String key = file + join + encoding;
    Node root = cache.get(key);
    if (root == null) {
      root = new TreeParser(application).parse(new TokenParser(
          application.getConfiguration().getResourceLocater().getString(file, encoding)));
      root = new TreeRebuilder(application, file).refactor(root);
      cache.put(key, root);
    }
    return root;
  }
}
