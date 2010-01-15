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
package net.asfun.jangod.interpret;

import java.util.ArrayList;
import java.util.List;

import net.asfun.jangod.lib.Tag;
import net.asfun.jangod.lib.TagLibrary;
import net.asfun.jangod.parse.JangodParser;
import net.asfun.jangod.parse.TagToken;

public class TagNode implements Node{

	private int level;
	private TagToken master;
	private List<Node> carries;
	private String endTagName;
	private Tag tag;

	public TagNode(TagToken token, JangodParser parser, int lvl) throws InterpretException {
		master = token;
		level = lvl;
		tag = TagLibrary.getTag(master.getTagName());
		if ( tag == null ) {
			throw new InterpretException("Can't find tag >>> " + master.getTagName());
		}
		endTagName = tag.getEndTagName();
		if ( endTagName != null ) {
			carries = NodeList.makeList(parser, endTagName, level + 1);
		} else {
			carries = new ArrayList<Node>(0);
		}
	}

	@Override
	public String render(JangodInterpreter interperter) throws InterpretException {
		interperter.setLevel(level);
		return tag.interpreter(carries, master.getHelpers(), interperter);
	}
	
	@Override
	public String toString() {
//		return "[TagNode:" + tag.getName() + "]";
		return tag.getName();
	}
}
