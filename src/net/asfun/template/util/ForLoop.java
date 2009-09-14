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
package net.asfun.template.util;

import java.util.Iterator;
import static net.asfun.template.util.logging.JangodLogger;

@SuppressWarnings("unchecked")
public class ForLoop implements Iterator{

	private int index = -1;
	private int counter = 0;
	private int revindex = -9; //if don't know length
	private int revcounter = -9; //if don't know length
	private int length = -9; //if don't know length
	private boolean first = true;
	private boolean last;
	
	private Iterator<Object> it;
	
	public ForLoop(Iterator ite, int len) {
		length = len;
		if (len < 2) {
			revindex = 1;
			revcounter = 2;
			last = true;
		} else {
			revindex = len;
			revcounter = len + 1;
			last = false;
		}
		it = ite;
	}
	
	public ForLoop(Iterator ite) {
		it = ite;
		if ( it.hasNext() ) {
			last = false;
		} else {
			length = 0;
			revindex = 1;
			revcounter = 2;
			last = true;
		}
	}
	
	public Object next() {
		Object res;
		if ( it.hasNext() ) {
			index++;
			counter++;
			if ( length != -9 ) {
				revindex--;
				revcounter--;
			}
			res = it.next();
			if ( ! it.hasNext() ) {
				last = true;
				length = counter;
				revindex = 0;
				revcounter = 1;
			}
			if ( index > 0 ) {
				first = false;
			}
		} else {
			res = null;
		}
		return res;
	}

	public int getIndex() {
		return index;
	}

	public int getCounter() {
		return counter;
	}

	public int getRevindex() {
		if ( revindex == -9 ) {
			JangodLogger.warning("can't compute items' length while looping.");
		}
		return revindex;
	}

	public int getRevcounter() {
		if ( revcounter == -9 ) {
			JangodLogger.warning("can't compute items' length while looping.");
		}
		return revcounter;
	}

	public int getLength() {
		if ( revcounter == -9 ) {
			JangodLogger.warning("can't compute items' length while looping.");
		}
		return length;
	}

	public boolean isFirst() {
		return first;
	}

	public boolean isLast() {
		return last;
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
