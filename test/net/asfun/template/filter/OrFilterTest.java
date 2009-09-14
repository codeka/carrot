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

import net.asfun.template.compile.CompilerException;

import org.junit.Before;
import org.junit.Test;

public class OrFilterTest extends ZzzBase{

	@Before
	public void setUp() throws Exception {
		filter = new OrFilter();
		compiler.assignEngineScope("var1", "hello");
		compiler.assignEngineScope("var2", "");
		compiler.assignEngineScope("var3", null);
		compiler.assignEngineScope("var4", new Object[]{});
		compiler.assignEngineScope("var5", new ArrayList<Object>());
		compiler.assignEngineScope("var6", 0);
		compiler.assignEngineScope("var7", 0.0f);
		compiler.assignEngineScope("var8", 21);
		compiler.assignEngineScope("var9", compiler);
	}

	@Test
	public void test1() throws CompilerException {
		Boolean res = (Boolean) filter.filter(1, compiler);
		assertEquals(true, res);
	}
	
	@Test
	public void test2() throws CompilerException {
		Boolean res = (Boolean) filter.filter("", compiler, "var1", "var3");
		assertEquals(true, res);
	}
	
	@Test
	public void test3() throws CompilerException {
		Boolean res = (Boolean) filter.filter("", compiler, "var3", "var8");
		assertEquals(true, res);
	}
	
	@Test
	public void test4() throws CompilerException {
		Boolean res = (Boolean) filter.filter(-0l, compiler, "var5", "var4");
		assertEquals(false, res);
	}
	
	@Test
	public void test5() throws CompilerException {
		Boolean res = (Boolean) filter.filter(-02l, compiler, "var8", "var9");
		assertEquals(true, res);
	}
	
	@Test
	public void testGetName() {
		assertEquals("or", filter.getName());
	}

}
