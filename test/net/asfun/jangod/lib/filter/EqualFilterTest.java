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
package net.asfun.jangod.lib.filter;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import net.asfun.jangod.interpret.InterpretException;

import org.junit.Before;
import org.junit.Test;

public class EqualFilterTest extends ZzzBase{

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		filter = new EqualFilter();
		compiler.assignSessionScope("var1", 
				new String[] {"abc", "def", null, "ghi", "xyz", "123", "aaa"});
		compiler.assignSessionScope("var2", "asdfghjkl");
		compiler.assignSessionScope("var3", null);
		compiler.assignSessionScope("var4", 234);
		compiler.assignSessionScope("var5", compiler);
		HashMap map = new HashMap();
		map.put("a", "aaa");
		map.put("b", "bbb");
		map.put("c", "ccc");
		map.put("d", null);
		compiler.assignSessionScope("var6", map);
		ArrayList al = new ArrayList();
		al.add("ddd");
		al.add("ccc");
		al.add(234);
		al.add(map);
		al.add(al);
		al.add("abc");
		al.add(null);
		al.add(true);
		compiler.assignSessionScope("var7", al);
		compiler.assignSessionScope("var8", "bcd");
	}
	
	@Test
	public void test1() throws InterpretException {
		Object res = filter.filter("bcd", compiler, "var8");
		assertEquals(true, res);
	}
	
	@Test
	public void test2() throws InterpretException {
		Object obj = compiler.fetchSessionScope("var4");
		Object res = filter.filter(obj, compiler, "'234'");
		assertEquals(true, res);
	}
	
	@Test
	public void test3() throws InterpretException {
		Object obj = compiler.fetchSessionScope("var3");
		Object res = filter.filter(obj, compiler, "'234'");
		assertEquals(false, res);
	}
	
	@Test
	public void test4() throws InterpretException {
		Object obj = compiler.fetchSessionScope("var3");
		Object res = filter.filter(obj, compiler, "'234'");
		assertEquals(false, res);
	}
	
	@Test
	public void test5() throws InterpretException {
		Object obj = compiler.fetchSessionScope("var5");
		Object res = filter.filter(obj, compiler, "var5");
		assertEquals(true, res);
	}
	
	@Test
	public void testGetName() {
		assertEquals("equal", filter.getName());
	}

}
