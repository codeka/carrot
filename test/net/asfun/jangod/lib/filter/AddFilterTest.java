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

import java.math.BigInteger;


import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.lib.Filter;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AddFilterTest extends ZzzBase{

	@Before
	public void setUp() throws Exception {
		filter = new AddFilter();
	}

	@Test
	public void testInt() throws InterpretException {
		Object res = filter.filter(562, compiler, "56");
		assertEquals(618, res);
	}
	
	@Test
	public void testInteger() throws InterpretException {
		Object res = filter.filter(new Integer(-298), compiler, "\"23\"");
		assertEquals(-275, res);
	}
	
	@Test(expected=InterpretException.class)
	public void testFloat() throws InterpretException {
		Object res = filter.filter(new Double(-20.24), compiler, "abc");
		assertEquals(20.24f, (Double)res, 0.01f);
	}
	
	@Test
	public void testDouble() throws InterpretException {
		compiler.assignRuntimeScope("var1", 25);
		Object res = filter.filter(new Double(-20.24), compiler, "var1");
		assertEquals(4.76f, (Double)res, 0.01f);
	}
	
	@Test
	public void testLong() throws InterpretException {
		Object res = filter.filter(new Long(-0), compiler, new String[]{"2"});
		assertEquals(2, res);
	}
	
	@Test
	public void testShort() throws InterpretException {
		Object res = filter.filter(new Short((short) -22222222), compiler, "'2'");
		assertEquals((short)-22222220, res);
	}
	
	@Test
	public void testByte() throws InterpretException {
		Object res = filter.filter(new Byte((byte) 222), compiler, "-3");
		assertEquals((byte)219, res);
	}
	
	@Test
	public void testString() throws InterpretException {
		Object res = filter.filter("-215.5256", compiler, "-15.2");
		assertEquals(-230.72559d, (Double)res, 0.0001d);
	}
	
	@Test
	public void testBigInt() throws InterpretException {
		Object res = filter.filter(BigInteger.valueOf(-1547898522234l), compiler, "2234");
		assertEquals(BigInteger.valueOf(-1547898520000l), res);
	}
	
	@Test(expected=InterpretException.class)
	public void testString2() throws InterpretException {
		Object res = filter.filter("abcd", compiler, "2");
		assertEquals(12, res);
	}
	
	@Test
	public void testOther() throws InterpretException {
		Filter af = new AbsFilter();
		Object res = filter.filter(af, compiler, "1");
		assertEquals(af, res);
	}

	@Ignore
	public void testGetName() {
		assertEquals("add", filter.getName());
	}
}
