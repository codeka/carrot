/**********************************************************************
Copyright (c) 2010 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.jangod.tree;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.IOException;

import net.asfun.jangod.base.Application;
import net.asfun.jangod.base.Configuration;
import net.asfun.jangod.cache.StatelessObjectStorage;
import net.asfun.jangod.parse.TokenParser;

public class ParseResultManager {

	StatelessObjectStorage<String, Node> cache;
	Application application;
	static final String join = "@";
	
	public ParseResultManager (Application application) {
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
		if ( root == null ) {
			root = new TreeParser(application).parse(new TokenParser(
					application.getConfiguration().getResourceLocater().getString(file, encoding)));
			root = new TreeRebuilder(application, file).refactor(root);
			cache.put(key, root);
		}
		return root;
	}
}
