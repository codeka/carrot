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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.asfun.jangod.base.Constants;

public class FilterParser implements Serializable{

	private static final long serialVersionUID = 2328268066571880284L;
	
	private String content;
	private String var;
	private List<String> filters;
	private List<String[]> argss;

	public FilterParser(String cont) {
		content = cont;
	}
	
	public String getVariable() {
		return var;
	}
	
	/**
	 * get the filters
	 * @return filters
	 */
	public List<String> getFilters() {
		return filters;
	}
	
	/**
	 * get filters' args
	 * @return
	 */
	public List<String[]> getArgss() {
		return argss;
	}
	
	/**
	 * Get var and filters
	 * Like that if
	 *     image = {{obj.attr.attr|filter1:"ar|g1",arg2|filter2:'a:",b"c' }}
	 * then
	 *     var = obj.attr.attr
	 *     filter = [
	 *         [filter1,ar|g1,arg2],
	 *         [filter2,a:",b"c]   ]
	 * @throws ParseException 
	 */
	public void parse() throws ParseException {
		filters = new ArrayList<String>();
		argss = new ArrayList<String[]>();
		//image = obj.attr.attr|filter1:"ar|g1",arg2|filter2:'a:",b"c'
		int pointer = content.indexOf(VL);
		if ( pointer < 0 ) {
			var = content;
		} else {
			var = content.substring(0, pointer).trim();
			content = content.substring(pointer+1).trim();
			while ( content.length() > 0 ) {
				parseFilter(content);
			}
		}
	}
	
	private void parseFilter(String filterString) throws ParseException {
		//filterString = filter1:"ar|g1",arg2|filter2:'a:",b"c'
		int postColon = filterString.indexOf(CL);
		int postPipe = filterString.indexOf(VL);
		//filterString = filter1
		if ( postColon == postPipe ) {
			filters.add(filterString);
			argss.add(null);
			content = Constants.STR_BLANK;
		}
		//filterString = filter1:argString|filter2
		if ( postColon > 0 && ( postColon < postPipe || postPipe < 0 )) {
			List<String> args = new ArrayList<String>();
			filters.add(filterString.substring(0, postColon).trim());
			String argString = filterString.substring(postColon+1).trim();
			do {
				argString = parseArg(argString, args);
			} while(argString != null);
			argss.add(args.toArray(new String[] {}));
		}
		//filterString = filter1|fitler2:arg
		if ( postPipe > 0 && ( postPipe < postColon || postColon < 0 )) {
			filters.add(filterString.substring(0, postPipe).trim());
			argss.add(null);
			content = filterString.substring(postPipe+1).trim();
		}
	}
	
	private String parseArg(String argString, List<String> args) throws ParseException {
		//"ar|g1:",arg2   or 'a:"b"|c'|filter2  or arg3
		if( argString.charAt(0) == DQ ) {
			//change to save quote in arg
//			argString = argString.substring(1);
//			int post = argString.indexOf(DQ);
			int post = argString.substring(1).indexOf(DQ);
			if ( post < 0 ) {
				throw new ParseException("Filter argument doesn't match quotes >>> " + getVariable());
			} else {
				//change to save quote in arg
//				args.add(argString.substring(0,post));
//				if( post < argString.length() - 2) {
//					argString = argString.substring(post+1).trim();
				args.add(argString.substring(0,post+2));
				if( post < argString.length() - 3) {
					argString = argString.substring(post+2).trim();
					if ( argString.charAt(0) == VL ) {
						content = argString.substring(1).trim();
						return null;
					} 
					else if ( argString.charAt(0) == CM ) {
						return argString.substring(1).trim();
					}
					else {
						throw new ParseException("Filter argument is illegal >>> " + getVariable());
					}
				}
			}
		}
		else if( argString.charAt(0) == SQ ) {
			//change to save quote in arg
//			argString = argString.substring(1);
//			int post = argString.indexOf(SQ);
			int post = argString.substring(1).indexOf(SQ);
			if ( post < 0 ) {
				throw new ParseException("Filter argument doesn't match quotes >>> " + getVariable());
			} else {
				//change to save quote in arg
//				args.add(argString.substring(0,post));
//				if( post < argString.length() - 2) {
//					argString = argString.substring(post+1).trim();
				args.add(argString.substring(0,post+2));
				if( post < argString.length() - 3) {
					argString = argString.substring(post+2).trim();
					if ( argString.charAt(0) == VL ) {
						content = argString.substring(1).trim();
						return null;
					} 
					else if ( argString.charAt(0) == CM ) {
						return argString.substring(1).trim();
					}
					else {
						throw new ParseException("Filter argument is illegal >>> " + getVariable());
					}
				}
			}
		}
		else {
			int postComma = argString.indexOf(CM);
			int postPipe = argString.indexOf(VL);
			if ( postComma > 0 && ( postPipe > postComma || postPipe < 0)) {
				args.add(argString.substring(0,postComma).trim());
				if( postComma < argString.length() - 1) {
					return argString.substring(postComma+1).trim();
				}
			}
			if ( postPipe >= 0 && ( postPipe < postComma || postComma < 0)) {
				if ( postPipe > 0 ) {
					args.add(argString.substring(0,postPipe).trim());
				}
				if( postPipe < argString.length() - 1) {
					//RETURN NULL start a new filter parse
					content = argString.substring(postPipe+1).trim();
					return null;
				}
			}
			if ( postComma == 0 ) {
				throw new ParseException("Filter lost some argument >>> " + getVariable());
			}
			if ( postComma == postPipe ) {
				args.add(argString);
			}
		}
		content = Constants.STR_BLANK;
		return null;
	}
}
