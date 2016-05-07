package au.com.codeka.carrot.lib.filter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.base.CarrotException;

public class Md5FilterTest extends ZzzBase {

  @Before
  public void setUp() {
    filter = new Md5Filter();
  }

  @Test
  public void testInt() throws CarrotException {
    Object res = filter.filter("anysome@gmail.com", compiler);
    assertEquals("b445188727e9256ae739014fcbe36f3f", res);
  }
}
