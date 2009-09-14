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
package net.asfun.template.parse;

import static net.asfun.template.parse.ParserConstants.*;

/**
 * Do something hard to be done by TagToken
 * @author fangchq<anysome@com.gmail>
 *
 */
public class InstToken extends Token {
	
	private String instName;
	private String helpers;

	public InstToken(String image) throws ParserException{
		super(image);
	}
	
	@Override
	public int getType() {
		return TOKEN_INST;
	}

	@Override
	protected void parse() throws ParserException{
		content = image.substring(2, image.length()-2).trim();
		int postBlank = content.indexOf(' ');
		if ( postBlank > 0 ) {
			instName = content.substring(0, postBlank);
			helpers = content.substring(postBlank).trim();
		}
		else {
			instName = content;
			helpers = "";
		}	
	}

	public String getInstName() {
		return instName;
	}
	
	public String getHelpers() {
		return helpers;
	}
	
	@Override
	public String toString() {
		if ( helpers.length() == 0) {
			return "[INST]\r\n" + instName;
		}
		return "[INST]\r\n" + instName + "\r\n\t" + helpers;
	}

}
