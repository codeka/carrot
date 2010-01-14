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

public class TagToken extends Token {
	
	private String tagName;
	private String helpers;

	public TagToken(String image) throws ParseException{
		super(image);
	}
	
	@Override
	public int getType() {
		return TOKEN_TAG;
	}

	/**
	 * Get tag name
	 */
	@Override
	protected void parse() {
		content = image.substring(2, image.length()-2).trim().replaceFirst("\\s", " ");
		int postBlank = content.indexOf(' ');
		if ( postBlank > 0 ) {
			tagName = content.substring(0, postBlank);
			helpers = content.substring(postBlank).trim();
		}
		else {
			tagName = content;
			helpers = "";
		}
	}
	
	public String getTagName() {
		return tagName.toLowerCase();
	}
	
	public String getHelpers() {
		return helpers;
	}
	
	@Override
	public String toString() {
		if ( helpers.length() == 0) {
			return "[TAG]\r\n" + tagName;
		}
		return "[TAG]\r\n" + tagName + "\r\n\t" + helpers;
	}

}
