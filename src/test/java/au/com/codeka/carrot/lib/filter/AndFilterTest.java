package au.com.codeka.carrot.lib.filter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.base.CarrotException;

public class AndFilterTest extends ZzzBase {

  @Before
  public void setUp() throws Exception {
    filter = new AndFilter();
    compiler.assignRuntimeScope("var1", "hello");
    compiler.assignRuntimeScope("var2", "");
    compiler.assignRuntimeScope("var3", null);
    compiler.assignRuntimeScope("var4", new Object[] {});
    compiler.assignRuntimeScope("var5", new ArrayList<Object>());
    compiler.assignRuntimeScope("var6", 0);
    compiler.assignRuntimeScope("var7", 0.0f);
    compiler.assignRuntimeScope("var8", 21);
    compiler.assignRuntimeScope("var9", compiler);
  }

  @Test
  public void test1() throws CarrotException {
    Boolean res = (Boolean) filter.filter(1, compiler);
    assertEquals(true, res);
  }

  @Test
  public void test2() throws CarrotException {
    Boolean res = (Boolean) filter.filter(12, compiler, "var1", "var3");
    assertEquals(false, res);
  }

  @Test
  public void test3() throws CarrotException {
    Boolean res = (Boolean) filter.filter(-78, compiler, "var1", "var4");
    assertEquals(false, res);
  }

  @Test
  public void test4() throws CarrotException {
    Boolean res = (Boolean) filter.filter(-56l, compiler, "var5", "var9");
    assertEquals(false, res);
  }

  @Test
  public void test5() throws CarrotException {
    Boolean res = (Boolean) filter.filter(-0l, compiler, "var8", "var9");
    assertEquals(false, res);
  }

  @Test
  public void testGetName() {
    assertEquals("and", filter.getName());
  }

}
