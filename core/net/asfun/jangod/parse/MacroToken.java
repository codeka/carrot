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

/**
 * Do something hard to be done by TagToken
 * @author anysome
 *
 */
public class MacroToken extends Token {
	
	private static final long serialVersionUID = -3710981054298651807L;
	
	private String macroName;
	private String helpers;

	public MacroToken(String image) throws ParseException{
		super(image);
	}
	
	@Override
	public int getType() {
		return TOKEN_MACRO;
	}

	@Override
	protected void parse() throws ParseException{
		content = image.substring(2, image.length()-2).trim().replaceFirst(SCPACE_STR, BLANK_STR);
		int postBlank = content.indexOf(' ');
		if ( postBlank > 0 ) {
			macroName = content.substring(0, postBlank);
			helpers = content.substring(postBlank).trim();
		}
		else {
			macroName = content;
			helpers = "";
		}	
	}

	public String getMacroName() {
		return macroName;
	}
	
	public String getHelpers() {
		return helpers;
	}
	
	@Override
	public String toString() {
		if ( helpers.length() == 0) {
			return "[MACRO]\r\n" + macroName;
		}
		return "[MACRO]\r\n" + macroName + "\r\n\t" + helpers;
	}

}
