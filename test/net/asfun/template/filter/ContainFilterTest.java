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

import java.util.ArrayList;
import java.util.HashMap;

import net.asfun.template.compile.CompilerException;

import org.junit.Before;
import org.junit.Test;

public class ContainFilterTest extends ZzzBase{

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		filter = new ContainFilter();
		compiler.assignEngineScope("var1", 
				new String[] {"abc", "def", null, "ghi", "xyz", "123", "aaa"});
		compiler.assignEngineScope("var2", "asdfghjkl");
		compiler.assignEngineScope("var3", null);
		compiler.assignEngineScope("var4", 234);
		compiler.assignEngineScope("var5", compiler);
		HashMap map = new HashMap();
		map.put("a", "aaa");
		map.put("b", "bbb");
		map.put("c", "ccc");
		map.put("d", null);
		compiler.assignEngineScope("var6", map);
		ArrayList al = new ArrayList();
		al.add("ddd");
		al.add("ccc");
		al.add(234);
		al.add(map);
		al.add(al);
		al.add("abc");
		al.add(null);
		al.add(true);
		compiler.assignEngineScope("var7", al);
		compiler.assignEngineScope("var8", "bcd");
	}

	@Test
	public void test1() throws CompilerException {
		Object res = filter.filter("abcd", compiler, "var8");
		assertEquals(true, res);
	}
	
	@Test
	public void test2() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "var6.a");
		assertEquals(true, res);
	}
	
	@Test
	public void test3() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "'def'");
		assertEquals(true, res);
	}
	
	@Test
	public void test4() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "var6");
		assertEquals(true, res);
	}
	
	@Test
	public void test5() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "var6.c");
		assertEquals(true, res);
	}
	
	@Test
	public void test6() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "var4");
		assertEquals(true, res);
	}
	
	@Test
	public void test7() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "var3");
		assertEquals(true, res);
	}
	
	@Test
	public void test8() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var1");
		Object res = filter.filter(obj, compiler, "var3");
		assertEquals(true, res);
	}
	
	@Test
	public void test9() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var2");
		Object res = filter.filter(obj, compiler, "'dfgh\"");
		assertEquals(true, res);
	}
	
	@Test(expected=CompilerException.class)
	public void test10() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var5");
		Object res = filter.filter(obj, compiler, "'dfgh\"");
		assertEquals(true, res);
	}
	
	@Test
	public void test11() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var6");
		Object res = filter.filter(obj, compiler, "'aaa'");
		assertEquals(true, res);
	}
	
	@Test
	public void test12() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var6");
		Object res = filter.filter(obj, compiler, "tv");
		assertEquals(true, res);
	}
	
	@Test
	public void test13() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "'234'");
		assertEquals(true, res);
	}
	
	@Test
	public void test16() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "234");
		assertEquals(true, res);
	}
	
	@Test
	public void test14() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "var7");
		assertEquals(true, res);
	}
	
	@Test
	public void test15() throws CompilerException {
		Object obj = compiler.fetchEngineScope("var7");
		Object res = filter.filter(obj, compiler, "'TRUe'");
		assertEquals(true, res);
	}
	
	@Test
	public void testGetName() {
		assertEquals("contain", filter.getName());
	}
}
