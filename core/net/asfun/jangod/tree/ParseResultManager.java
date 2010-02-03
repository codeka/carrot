package net.asfun.jangod.tree;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.IOException;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.base.Configuration;
import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.cache.StatelessObjectStorage;
import net.asfun.jangod.cache.SynchronousStorage;
import net.asfun.jangod.parse.TokenParser;

public class ParseResultManager {

	StatelessObjectStorage<String, Node> cache;
	Application application;
	
	public ParseResultManager (Application application) {
		this.application = application;
		init(application.getConfiguration());
	}
	
	@SuppressWarnings("unchecked")
	private void init(Configuration config) {
		String storeClass = config.getProperty("parse.cache");
		if ( storeClass == null ) {
			cache = new SynchronousStorage<String, Node>();
		} else {
			try {
				cache = (StatelessObjectStorage<String, Node>) Class.forName(storeClass).newInstance();
			} catch (Exception e) {
				cache = new SynchronousStorage<String, Node>();
				JangodLogger.warning("Can't instance parser cacher(use default) >>> " + storeClass);
			}
		}
	}
	
	public Node getParseResult(String file, String encoding) throws IOException {
		String key = file + "@" + encoding;
		Node root = cache.get(key);
		if ( root == null ) {
			root = TreeParser.parser(new TokenParser(ResourceManager.getResource(file, encoding)));
			new TreeRebuilder(application, file).rebuilder(root);
			cache.put(key, root);
		}
//		TreeIterator nit = new TreeIterator(root);
//		while (nit.hasNext()) {
//			System.out.println(nit.next());
//		}
		return root;
	}
}
