package net.asfun.template.filter;

import static org.junit.Assert.assertEquals;
import net.asfun.template.compile.CompilerException;

import org.junit.Before;
import org.junit.Test;

public class Md5FilterTest extends ZzzBase{

	@Before
	public void setUp() throws Exception {
		filter = new Md5Filter();
	}
	
	@Test
	public void testInt() throws CompilerException {
		Object res = filter.filter("anysome@gmail.com", compiler);
		assertEquals(20, res);
	}
}
