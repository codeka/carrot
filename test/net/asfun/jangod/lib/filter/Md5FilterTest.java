package net.asfun.jangod.lib.filter;

import static org.junit.Assert.assertEquals;
import net.asfun.jangod.interpret.InterpretException;

import org.junit.Before;
import org.junit.Test;

public class Md5FilterTest extends ZzzBase{

	@Before
	public void setUp() throws Exception {
		filter = new Md5Filter();
	}
	
	@Test
	public void testInt() throws InterpretException {
		Object res = filter.filter("anysome@gmail.com", compiler);
		assertEquals("b445188727e9256ae739014fcbe36f3f", res);
	}
}
