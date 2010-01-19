/**********************************************************************
Copyright (c) 2009 Asfun Net.

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
package net.asfun.jangod.lib.tag;

import java.io.IOException;
import java.util.List;

import net.asfun.jangod.base.Context;
import net.asfun.jangod.base.UrlResourceLoader;
import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.interpret.Node;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.parse.JangodParser;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.util.HelperStringTokenizer;

/**
 * {% include 'sidebar.html' %}
 * {% include var_fileName %}
 * @author anysome
 *
 */
public class IncludeTag implements Tag{
	
	final String TAGNAME = "include";
	
	@Override
	public String interpreter(List<Node> carries, String helpers, JangodInterpreter interpreter)
			throws InterpretException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if( helper.length != 1) {
			throw new InterpretException("Tag 'include' expects 1 helper >>> " + helper.length);
		}
		String templateFile = interpreter.resolveString(helper[0]);
		try {
			UrlResourceLoader loader = new UrlResourceLoader(
					interpreter.getConfig().getEncoding(), interpreter.getConfig().getWorkspace());
			JangodParser parser = new JangodParser(loader.getReader(templateFile));
			JangodInterpreter child = interpreter.clone();
			child.assignRuntimeScope(Context.INSERT_FLAG, true, 1);
			return child.render(parser);
		} catch (IOException e) {
			throw new InterpretException(e.getMessage());
		} catch (ParseException e) {
			throw new InterpretException(e.getMessage());
		}
	}

	@Override
	public String getEndTagName() {
		return null;
	}

	@Override
	public String getName() {
		return TAGNAME;
	}

}
