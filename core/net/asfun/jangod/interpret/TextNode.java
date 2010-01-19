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

import net.asfun.jangod.parse.FixedToken;
//import net.asfun.jangod.parse.Token;

public class TextNode implements Node{

	//if NodeList DONT add NoteToken
	
//	public TextNode(Token tk) {
//		token = tk;
//	}
//	
//	private Token token;
//
//	@Override
//	public String render(JangodInterpreter interperter) {
//		if ( token instanceof FixedToken ) {
//			return ((FixedToken) token).output();
//		} else {
//			return "";
//		}
//	}
	
	private FixedToken token;
	
	public TextNode(FixedToken tk) {
		token = tk;
	}

	@Override
	public String render(JangodInterpreter interperter)
			throws InterpretException {
		return token.output();
	}

	
}
