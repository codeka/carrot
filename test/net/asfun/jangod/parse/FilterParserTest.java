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

import static org.junit.Assert.*;

import org.junit.Test;


public class FilterParserTest {

	FilterParser parser;
	String varStr;
	
	@Test
	public void test1() throws ParseException {
		varStr = "abc.a|f1:abc,b|f2";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("abc.a", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals(2, parser.getArgss().get(0).length);
	}
	
	@Test(expected=ParseException.class)
	public void test2() throws ParseException {
		varStr = "abc.a|f1:'abc\",b|f2";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("abc.a", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals(2, parser.getArgss().get(0).length);
	}
	
	@Test
	public void test3() throws ParseException {
		varStr = "abc.a|f1:\"abc\",b|f2";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("abc.a", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals(2, parser.getArgss().get(0).length);
	}
	
	@Test
	public void test4() throws ParseException {
		varStr = "'abc.a'|f1:\"abc\",b|f2";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("'abc.a'", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals(2, parser.getArgss().get(0).length);
	}
	
	@Test
	public void test5() throws ParseException {
		varStr = "'abc.a'|f1:\"a'b|c,\",b|f2";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("'abc.a'", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals(2, parser.getArgss().get(0).length);
	}
	
	@Test
	public void test6() throws ParseException {
		varStr = "'abc.a'|f1:\"a'b|c,\"|f2:bec,xz";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("'abc.a'", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals("f1",parser.getFilters().get(0));
		assertEquals("\"a'b|c,\"",parser.getArgss().get(0)[0]);
		assertEquals("bec",parser.getArgss().get(1)[0]);
		assertEquals(2, parser.getArgss().get(1).length);
	}
	
	@Test
	public void test7() throws ParseException {
		varStr = "'abc.a'|f1:\"a'b|c,\",|f2:bec,xz|";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("'abc.a'", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals("f1",parser.getFilters().get(0));
		assertEquals("\"a'b|c,\"",parser.getArgss().get(0)[0]);
		assertEquals(1, parser.getArgss().get(0).length);
		assertEquals("bec",parser.getArgss().get(1)[0]);
		assertEquals("xz",parser.getArgss().get(1)[1]);
		assertEquals(2, parser.getArgss().get(1).length);
	}
	
	@Test(expected=ParseException.class)
	public void test8() throws ParseException {
		varStr = "'abc.a'|f1:\"a'b|c,\",,|f2:bec,xz|";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("'abc.a'", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals("f1",parser.getFilters().get(0));
		assertEquals("\"a'b|c,\"",parser.getArgss().get(0)[0]);
		assertEquals(1, parser.getArgss().get(0).length);
		assertEquals("bec",parser.getArgss().get(1)[0]);
		assertEquals(2, parser.getArgss().get(1).length);
	}
	
	@Test
	public void test9() throws ParseException {
		varStr = "'abc.a'|f1:\"a'b|c,\",|f2:bec,xz,";
		parser = new FilterParser(varStr);
		parser.parse();
		assertEquals("'abc.a'", parser.getVariable());
		assertEquals(2, parser.getFilters().size());
		assertEquals("f1",parser.getFilters().get(0));
		assertEquals("\"a'b|c,\"",parser.getArgss().get(0)[0]);
		assertEquals(1, parser.getArgss().get(0).length);
		assertEquals("bec",parser.getArgss().get(1)[0]);
		assertEquals(2, parser.getArgss().get(1).length);
	}

}
