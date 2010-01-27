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
package net.asfun.jangod.parse;

import static net.asfun.jangod.parse.ParserConstants.*;

public class Tokenizer{

	private char[] is;
	private int currPost = 0;
	private int tokenStart = 0;
	private int tokenLength = 0;
	private int tokenKind = -1;
	private int length = 0;
	private int lastStart = 0;
	private int inComment = 0;
	
	public void init(String inputstream) {
		is = inputstream.toCharArray();
		length = inputstream.length();
		currPost = 0;
		tokenStart = 0;
		tokenKind = -1;
		lastStart = 0;
		inComment = 0;
	}
	
	public Token getNextToken() throws ParseException {
		char c = 0;
		while ( currPost < length ) {
			c = is[currPost++];
			if ( currPost == length ) {
				return getEndToken();
			}
			switch( c ) {
			//mayby a new token is starting
			case TOKEN_PREFIX :
				if ( currPost < length ) {
					c = is[currPost];
					switch( c ) {
					case TOKEN_NOTE :
						if ( inComment++ > 0 ) {
							continue;
						}
						tokenLength = currPost-tokenStart-1;
						if ( tokenLength > 0 ) {
							//start a new token
							lastStart = tokenStart;
							tokenStart = --currPost;
							tokenKind = c;
							inComment--;
							return newToken(TOKEN_FIXED);
						} else {
							tokenKind = c;
						}
						break;
					case TOKEN_INST :
					case TOKEN_TAG :
					case TOKEN_ECHO :
						if ( inComment > 0 ) {
							continue;
						}
						//match token two ends
						if ( ! matchToken(c) && tokenKind > 0 ) {
								continue;
						}
						tokenLength = currPost-tokenStart-1;
						if ( tokenLength > 0 ) {
							//start a new token
							lastStart = tokenStart;
							tokenStart = --currPost;
							tokenKind = c;
							return newToken(TOKEN_FIXED);
						} else {
							tokenKind = c;
						}
						break;
					default :
						//nothing continue;
					}
				}
				//reach the stream end
				else {
					return getEndToken();
				}
				break;
			//mayby current token is closing	
			case TOKEN_INST :
			case TOKEN_TAG :
			case TOKEN_ECHO2 :
				//match token two ends
				if ( inComment > 0 ) {
					continue;
				}
				if ( ! matchToken(c) ) {
					continue;
				}
				if ( currPost < length ) {
					c = is[currPost];
					if ( c == TOKEN_POSTFIX ) {
						tokenLength = currPost-tokenStart+1;
						if ( tokenLength > 0 ) {
							//start a new token
							lastStart = tokenStart;
							tokenStart = ++currPost;
							int kind = tokenKind;
							tokenKind = TOKEN_FIXED;
							return newToken(kind);
						}
					}
				} else {
					return getEndToken();
				}
				break;
			case TOKEN_NOTE :
				if ( ! matchToken(c) ) {
					continue;
				}
				if ( currPost < length ) {
					c = is[currPost];
					if ( c == TOKEN_POSTFIX ) {
						if ( --inComment > 0 ) {
							continue;
						}
						tokenLength = currPost-tokenStart+1;
						if ( tokenLength > 0 ) {
							//start a new token
							lastStart = tokenStart;
							tokenStart = ++currPost;
							tokenKind = TOKEN_FIXED;
							return newToken(TOKEN_NOTE);
						}
					}
				} else {
					return getEndToken();
				}
				break;
			default:
				if (tokenKind == -1) {
					tokenKind = TOKEN_FIXED;
				}
			}
		}
		return null;
	}
	
	private Token getEndToken() throws ParseException {
		tokenLength = currPost-tokenStart;
		int type = TOKEN_FIXED;
		if ( inComment > 0 ) {
			type = TOKEN_NOTE;
		}
		return Token.newToken(type, String.valueOf(is, tokenStart, tokenLength));
	}
	
	private Token newToken(int kind) throws ParseException {
		Token token = Token.newToken(kind, String.copyValueOf(is, lastStart, tokenLength));
		return token;
	}
	
	private boolean matchToken(char kind) {
		if ( kind == TOKEN_ECHO ) {
			return tokenKind == TOKEN_ECHO2;
		} else if ( kind == TOKEN_ECHO2 ) {
			return tokenKind == TOKEN_ECHO;
		} else {
			return kind == tokenKind;
		}
	}
	
}
