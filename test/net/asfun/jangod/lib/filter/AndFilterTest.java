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


import static org.junit.Assert.*;

import java.util.ArrayList;


import net.asfun.jangod.interpret.InterpretException;

import org.junit.Before;
import org.junit.Test;

public class AndFilterTest extends ZzzBase{

	@Before
	public void setUp() throws Exception {
		filter = new AndFilter();
		compiler.assignSessionScope("var1", "hello");
		compiler.assignSessionScope("var2", "");
		compiler.assignSessionScope("var3", null);
		compiler.assignSessionScope("var4", new Object[]{});
		compiler.assignSessionScope("var5", new ArrayList<Object>());
		compiler.assignSessionScope("var6", 0);
		compiler.assignSessionScope("var7", 0.0f);
		compiler.assignSessionScope("var8", 21);
		compiler.assignSessionScope("var9", compiler);
	}
	
	@Test
	public void test1() throws InterpretException {
		Boolean res = (Boolean) filter.filter(1, compiler);
		assertEquals(true, res);
	}
	
	@Test
	public void test2() throws InterpretException {
		Boolean res = (Boolean) filter.filter(12, compiler, "var1", "var3");
		assertEquals(false, res);
	}
	
	@Test
	public void test3() throws InterpretException {
		Boolean res = (Boolean) filter.filter(-78, compiler, "var1", "var4");
		assertEquals(false, res);
	}
	
	@Test
	public void test4() throws InterpretException {
		Boolean res = (Boolean) filter.filter(-56l, compiler, "var5", "var9");
		assertEquals(false, res);
	}
	
	@Test
	public void test5() throws InterpretException {
		Boolean res = (Boolean) filter.filter(-0l, compiler, "var8", "var9");
		assertEquals(false, res);
	}
	
	@Test
	public void testGetName() {
		assertEquals("and", filter.getName());
	}

}
