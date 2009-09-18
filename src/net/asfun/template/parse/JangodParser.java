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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static net.asfun.template.util.logging.*;

public class JangodParser implements Iterator<Token>{
	
	private TokenManager tm = new TokenManager();
	private Token token;
	private boolean proceeding = true;

	public JangodParser(String script) {
		tm.init(script);
	}

	public JangodParser(Reader reader) throws ParserException {
		BufferedReader br = new BufferedReader(reader);
		StringBuffer buff = new StringBuffer();
		String line;
		try {
			while( (line=br.readLine()) != null ) {
				buff.append(line);
				buff.append("\n");
			}
		} catch (IOException e) {
			throw new ParserException("read template reader fault.");
		}
		tm.init(buff.toString());
	}

	@Override
	public boolean hasNext(){
		if ( proceeding ) {
			try {
				token = tm.getNextToken();
				if ( token != null ) {
					return true;
				} else {
					proceeding = false;
					return false;
				}
			} catch (ParserException e) {
				JangodLogger.log(Level.SEVERE, e.getMessage(), e.getCause());
				token = null;
				//TODO go on proceeding or not
			}
		}	
		return false;
	}

	@Override
	public Token next() {
		if ( proceeding ) {
			if ( token == null ) {
				try {
					Token tk = tm.getNextToken();
					if ( tk == null ) {
						proceeding = false;
						throw new NoSuchElementException();
					}
					return tk;
				} catch (ParserException e) {
					JangodLogger.log(Level.SEVERE, e.getMessage(), e.getCause());
					//go on proceeding or not
					throw new NoSuchElementException();
				}
			} else {
				Token last = token;
				token = null;
				return last;
			}
		}
		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
