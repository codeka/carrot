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
package net.asfun.jangod.lib.macro;

import java.io.IOException;

import net.asfun.jangod.base.ResourceManager;
import net.asfun.jangod.lib.Macro;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.parse.TokenParser;
import net.asfun.jangod.tree.Node;
import net.asfun.jangod.tree.TreeParser;
import net.asfun.jangod.tree.TreeRebuilder;
import net.asfun.jangod.util.HelperStringTokenizer;

public class IncludeMacro implements Macro{

	final String MACRONAME = "include";
	
	@Override
	public String getEndMacroName() {
		return null;
	}

	@Override
	public void refactor(Node current, String helpers, TreeRebuilder rebuilder) throws ParseException {
		String[] helper = new HelperStringTokenizer(helpers).allTokens();
		if( helper.length != 1) {
			throw new ParseException("Macro 'include' expects 1 helper >>> " + helper.length);
		}
		String templateFile = rebuilder.resolveString(helper[0]);
		try {
			String fullName = ResourceManager.getFullName(templateFile, 
					rebuilder.getWorkspace(), rebuilder.getConfiguration().getWorkspace());
			//TODO STOP LOOP INCLUDE
			Node includeRoot = TreeParser.parser( new TokenParser( ResourceManager.getResource(
					fullName, rebuilder.getConfiguration().getEncoding())));
			
//			includeRoot.replaceWithChildren(current);
			rebuilder.nodeReplace(current, includeRoot.children());
		} catch (IOException e) {
			throw new ParseException(e.getMessage());
		}
	}

	@Override
	public String getName() {
		return MACRONAME;
	}

}
