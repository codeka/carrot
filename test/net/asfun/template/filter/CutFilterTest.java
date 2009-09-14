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
package net.asfun.template.filter;


import static org.junit.Assert.assertEquals;

import net.asfun.template.compile.CompilerException;

import org.junit.Before;
import org.junit.Test;

public class CutFilterTest extends ZzzBase{

	@Before
	public void setUp() throws Exception {
		filter = new CutFilter();
		compiler.assignEngineScope("var1", "a.bc,ef\\,g[]*.~^/$abce ");
		compiler.assignEngineScope("var2", "aaaabbbbccccddddfffff");
		compiler.assignEngineScope("var3", "1234123412341234	 	1234");
		compiler.assignEngineScope("var4", "&absp;\"<abc>''adfl'\"\"</abc>&gt;&amp;");
	}
	
	@Test
	public void test1() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "'a'");
		assertEquals(".bc,ef\\,g[]*.~^/$bce ", res);
	}
	
	@Test
	public void test2() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "a");
		assertEquals(".bc,ef\\,g[]*.~^/$bce ", res);
	}
	
	@Test
	public void test3() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "'.'");
		assertEquals("abc,ef\\,g[]*~^/$abce ", res);
	}
	
	@Test
	public void test9() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "'^'");
		assertEquals("a.bc,ef\\,g[]*.~/$abce ", res);
	}
	
	@Test
	public void test4() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "'\\'");
		assertEquals("a.bc,ef,g[]*.~^/$abce ", res);
	}
	
	@Test
	public void test5() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var2");
		Object res = filter.filter(obj, compiler, "'c'");
		assertEquals("aaaabbbbddddfffff", res);
	}
	
	@Test
	public void test6() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var3");
		Object res = filter.filter(obj, compiler, "2");
		assertEquals("134134134134	 	134", res);
	}
	
	@Test
	public void test7() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var3");
		Object res = filter.filter(obj, compiler, "' '");
		assertEquals("1234123412341234		1234", res);
	}
	
	@Test
	public void test8() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var4");
		Object res = filter.filter(obj, compiler, "&");
		assertEquals("absp;\"<abc>''adfl'\"\"</abc>gt;amp;", res);
	}
	
	@Test
	public void test10() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var4");
		Object res = filter.filter(obj, compiler, "'''");
		assertEquals("&absp;\"<abc>adfl\"\"</abc>&gt;&amp;", res);
	}
	
	@Test
	public void test11() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var4");
		Object res = filter.filter(obj, compiler, "'\"'");
		assertEquals("&absp;<abc>''adfl'</abc>&gt;&amp;", res);
	}
	
	@Test
	public void test12() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var4");
		Object res = filter.filter(obj, compiler, "'>'");
		assertEquals("&absp;\"<abc''adfl'\"\"</abc&gt;&amp;", res);
	}
	
	@Test
	public void test13() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var4");
		Object res = filter.filter(obj, compiler, "'/'");
		assertEquals("&absp;\"<abc>''adfl'\"\"<abc>&gt;&amp;", res);
	}

	@Test
	public void testGetName() {
		assertEquals("cut", filter.getName());
	}
}
