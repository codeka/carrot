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

import static net.asfun.jangod.parse.ParserConstants.TOKEN_ECHO;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_FIXED;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_MACRO;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_NOTE;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_TAG;
import static net.asfun.jangod.util.logging.JangodLogger;

import net.asfun.jangod.parse.EchoToken;
import net.asfun.jangod.parse.FixedToken;
import net.asfun.jangod.parse.MacroToken;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.parse.TagToken;
import net.asfun.jangod.parse.Token;
import net.asfun.jangod.parse.TokenParser;
import net.asfun.jangod.util.logging.Level;

public class TreeParser {

	public static Node parser(TokenParser parser) {
		Node root = new RootNode();
		tree(root, parser, RootNode.TREE_ROOT_END);
		return root;
	}
	
	static void tree(Node node, TokenParser parser, String endName) {
		Token token;
		TagToken tag;
		MacroToken macro;
		while( parser.hasNext() ) {
			token = parser.next();
			switch(token.getType()) {
				case TOKEN_FIXED :
					TextNode tn = new TextNode((FixedToken)token);
					node.add(tn);
					break;
				case TOKEN_NOTE :
					break;
				case TOKEN_MACRO :
					macro = (MacroToken) token;
					if ( macro.getMacroName().equalsIgnoreCase(endName) ) {
						return;
					}
					try {
						MacroNode mn = new MacroNode((MacroToken) token);
						node.add(mn);
						if ( mn.endName != null ) {
							tree(mn, parser, mn.endName);
						}
					} catch (ParseException e) {
						JangodLogger.log(Level.WARNING, "can't create node with token >>> " + token, e.getCause());
					}	
					break;
				case TOKEN_ECHO :
					VariableNode vn = new VariableNode((EchoToken) token);
					node.add(vn);
					break;
				case TOKEN_TAG :
					tag = (TagToken) token;
					if ( tag.getTagName().equalsIgnoreCase(endName) ) {
						return;
					}
					try {
						TagNode tg = new TagNode((TagToken) token);
						node.add(tg);
						if ( tg.endName != null ) {
							tree(tg, parser, tg.endName);
						}
					} catch (ParseException e) {
						JangodLogger.log(Level.WARNING, "can't create node with token >>> " + token, e.getCause());
					}
					break;
				default :
					JangodLogger.warning("unknown type token >>> " + token);
			}
		}
		//can't reach end tag
		if( endName != null && !endName.equals(RootNode.TREE_ROOT_END) ) {
			JangodLogger.severe("lost end for tag or macro >>> " + endName);
		}
	}
}
