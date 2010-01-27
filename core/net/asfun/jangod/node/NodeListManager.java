package net.asfun.jangod.node;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.IOException;
import java.util.List;

import net.asfun.jangod.base.Configuration;
import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.cache.StatelessObjectStorage;
import net.asfun.jangod.cache.SynchronousStorage;
import net.asfun.jangod.parse.TokenParser;

public class NodeListManager {

	static StatelessObjectStorage<String, List<Node>> cache;
	
	static {
		init();
	}
	
	@SuppressWarnings("unchecked")
	private static void init() {
		Configuration config = Configuration.getDefault();
		String storeClass = config.getProperty("parser.cache");
		if ( storeClass == null ) {
			cache = new SynchronousStorage<String,List<Node>>();
		} else {
			try {
				cache = (StatelessObjectStorage<String, List<Node>>) Class.forName(storeClass).newInstance();
			} catch (Exception e) {
				cache = new SynchronousStorage<String,List<Node>>();
				JangodLogger.warning("Can't instance parser cacher(use default) >>> " + storeClass);
			}
		}
	}
	
	public static List<Node> getParseResult(String file, String encoding) throws IOException{
		String key = file + "@" + encoding;
		List<Node> result = cache.get(key);
		if ( result == null ) {
			result = NodeParser.makeList(new TokenParser(ResourceManager.getResource(file, encoding)), null, 1);
			cache.put(key, result);
		}
		return result;
	}
}
