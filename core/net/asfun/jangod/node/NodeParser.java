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
package net.asfun.jangod.node;

import static net.asfun.jangod.parse.ParserConstants.TOKEN_ECHO;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_FIXED;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_INST;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_NOTE;
import static net.asfun.jangod.parse.ParserConstants.TOKEN_TAG;
import static net.asfun.jangod.util.logging.JangodLogger;

import java.util.LinkedList;
import java.util.List;

import net.asfun.jangod.parse.EchoToken;
import net.asfun.jangod.parse.FixedToken;
import net.asfun.jangod.parse.InstToken;
import net.asfun.jangod.parse.TokenParser;
import net.asfun.jangod.parse.ParseException;
import net.asfun.jangod.parse.TagToken;
import net.asfun.jangod.parse.Token;
import net.asfun.jangod.util.logging.Level;


public class NodeParser {
	
	/**
	 * general the node tree
	 * @param parser
	 * @param endName
	 * @param path
	 * @return
	 */
	public static List<Node> makeList(TokenParser parser, String endName, int level) {
		List<Node> nodes = new LinkedList<Node>();
		Token token;
		TagToken tag;
		while( parser.hasNext() ) {
			token = parser.next();
			switch(token.getType()) {
				case TOKEN_FIXED :
					TextNode xn = new TextNode((FixedToken)token);
					nodes.add(xn);
					break;
				case TOKEN_NOTE :		
					//even not need to add this node, or, it could be add like a fixedtoken
					break;
				case TOKEN_INST :
					try {
						InstNode in = new InstNode((InstToken) token);
						nodes.add(in);
					} catch (ParseException e) {
						JangodLogger.log(Level.WARNING, "can't create node with token >>> " + token, e.getCause());
					}	
					break;
				case TOKEN_ECHO :
					VariableNode vn = new VariableNode((EchoToken) token, level);
					nodes.add(vn);
					break;
				case TOKEN_TAG :
					tag = (TagToken) token;
					if ( tag.getTagName().equals(endName) ) {
						return nodes;
					}
					try {
						TagNode tn = new TagNode((TagToken) token, parser, level);
						nodes.add(tn);
					} catch (ParseException e) {
						JangodLogger.log(Level.WARNING, "can't create node with token >>> " + token,e.getCause());
					}
					break;
				default :
					JangodLogger.warning("unknown type token >>> " + token);
			}
		}
		//can't reach end tag
		if(endName != null ) {
			JangodLogger.severe("lost end for tag or instruction >>> " + endName);
		}
		return nodes;
	}
}
