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
package net.asfun.template.compile;

import static net.asfun.template.parse.ParserConstants.*;
import static net.asfun.template.util.logging.*;

import java.util.ArrayList;
import java.util.List;

import net.asfun.template.parse.EchoToken;
import net.asfun.template.parse.InstToken;
import net.asfun.template.parse.JangodParser;
import net.asfun.template.parse.TagToken;
import net.asfun.template.parse.Token;

public class NodeList {
	
	/**
	 * general the node tree
	 * @param parser
	 * @param endTagName
	 * @param path
	 * @return
	 */
	public static List<Node> makeList(JangodParser parser, String endTagName, int level) {
		List<Node> nodes = new ArrayList<Node>();
		Token token;
		TagToken tag;
		while( parser.hasNext() ) {
			token = parser.next();
			switch(token.getType()) {
				case TOKEN_FIXED :
				case TOKEN_NOTE :		
					TextNode xn = new TextNode(token);
					nodes.add(xn);
					break;
				case TOKEN_INST :
					try {
						InstNode in = new InstNode((InstToken) token);
						nodes.add(in);
					} catch (CompilerException e) {
						JangodLogger.log(Level.WARNING, "can't create node with token >>> " + token, e.getCause());
					}	
					break;
				case TOKEN_ECHO :
					VariableNode vn = new VariableNode((EchoToken) token, level);
					nodes.add(vn);
					break;
				case TOKEN_TAG :
					tag = (TagToken) token;
					if ( tag.getTagName().equals(endTagName) ) {
						return nodes;
					}
					try {
						TagNode tn = new TagNode((TagToken) token, parser, level);
						nodes.add(tn);
					} catch (CompilerException e) {
						JangodLogger.log(Level.WARNING, "can't create node with token >>> " + token,e.getCause());
					}
					break;
				default :
					JangodLogger.warning("unknown type token >>> " + token);
			}
		}
		//can't reach end tag
		if(endTagName != null ) {
			JangodLogger.severe("lost end tag >>> " + endTagName);
		}
		return nodes;
	}
}
