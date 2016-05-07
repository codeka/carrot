package au.com.codeka.carrot.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.base.CarrotException;

public class VariableTest {

  private Object obj;
  private Variable var;
  private Object res;

  @Before
  public void setUp() throws Exception {
    obj = new Date();
  }

  @SuppressWarnings("deprecation") // Date.getDate() is deprecated, we don't care.
  @Test
  public void test1() throws CarrotException {
    var = new Variable("now.Date");
    res = var.resolve(obj);
    assertEquals(new Date().getDate(), res);
  }
}
