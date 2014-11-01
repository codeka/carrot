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
import java.io.Writer;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.NodeList;
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
	public void interpreter(NodeList carries, String helpers, JangodInterpreter interpreter,
			Writer writer) throws InterpretException, IOException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if( helper.length != 1) {
			throw new InterpretException("Tag 'include' expects 1 helper >>> " + helper.length);
		}
		String templateFile = interpreter.resolveString(helper[0]);
		String fullName = interpreter.getApplication().getConfiguration().getResourceLocater()
				.getFullName(templateFile, interpreter.getWorkspace(),
						interpreter.getConfiguration().getWorkspace());
		Node node = interpreter.getApplication().getParseResult(
				fullName, interpreter.getConfiguration().getEncoding() );
		JangodInterpreter child = interpreter.clone();
		child.assignRuntimeScope(JangodInterpreter.INSERT_FLAG, true, 1);
		child.render(node, writer);
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
