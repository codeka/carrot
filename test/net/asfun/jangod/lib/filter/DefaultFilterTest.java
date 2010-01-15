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

public class DefaultFilterTest extends ZzzBase{

	@Before
	public void setUp() throws Exception {
		filter = new DefaultFilter();
		compiler.assignRuntimeScope("var1", 1);
		compiler.assignRuntimeScope("var2", "hello");
		compiler.assignRuntimeScope("var3", new Long[]{1l, 3l, 4l});
		compiler.assignRuntimeScope("var4", 0);
		compiler.assignRuntimeScope("var5", null);
		compiler.assignRuntimeScope("var6", "");
		compiler.assignRuntimeScope("var7", new ArrayList<String>());
		compiler.assignRuntimeScope("var8", new HashMap<String,String>());
	}
	
	@Test
	public void test1() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var1");
		Object res = filter.filter(obj, compiler, "'a'");
		assertEquals(1, res);
	}
	
	@Test
	public void test2() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var4");
		Object res = filter.filter(obj, compiler, "'a'");
		assertEquals("a", res);
	}
	
	@Test
	public void test3() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var4");
		Object res = filter.filter(obj, compiler, "a");
		assertEquals("a", res);
	}
	
	@Test
	public void test4() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var5");
		Object res = filter.filter(obj, compiler, "var1");
		assertEquals(1, res);
	}
	
	@Test
	public void test5() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var5");
		Object res = filter.filter(obj, compiler, "'var1'");
		assertEquals("var1", res);
	}
	
	@Test(expected=InterpretException.class)
	public void test6() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var5");
		Object res = filter.filter(obj, compiler);
		assertEquals("var1", res);
	}
	
	@Test
	public void test7() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var4");
		Object res = filter.filter(obj, compiler, "var2");
		assertEquals("hello", res);
	}
	
	@Test
	public void test8() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var6");
		Object res = filter.filter(obj, compiler, "var2");
		assertEquals("hello", res);
	}
	
	@Test
	public void test9() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var7");
		Object res = filter.filter(obj, compiler, "var2");
		assertEquals("hello", res);
	}
	
	@Test
	public void test10() throws InterpretException {
		Object obj = compiler.fetchRuntimeScope("var8");
		Object res = filter.filter(obj, compiler, "var2");
		assertEquals("hello", res);
	}

}
