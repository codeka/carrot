package au.com.codeka.carrot.lib.filter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;

public class DefaultFilterTest extends ZzzBase {

  @Before
  public void setUp() throws Exception {
    filter = new DefaultFilter();
    compiler.assignRuntimeScope("var1", 1);
    compiler.assignRuntimeScope("var2", "hello");
    compiler.assignRuntimeScope("var3", new Long[] { 1l, 3l, 4l });
    compiler.assignRuntimeScope("var4", 0);
    compiler.assignRuntimeScope("var5", null);
    compiler.assignRuntimeScope("var6", "");
    compiler.assignRuntimeScope("var7", new ArrayList<String>());
    compiler.assignRuntimeScope("var8", new HashMap<String, String>());
  }

  @Test
  public void test1() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "'a'");
    assertEquals(1, res);
  }

  @Test
  public void test2() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "'a'");
    assertEquals("a", res);
  }

  @Test
  public void test3() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "a");
    assertEquals("a", res);
  }

  @Test
  public void test4() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var5");
    Object res = filter.filter(obj, compiler, "var1");
    assertEquals(1, res);
  }

  @Test
  public void test5() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var5");
    Object res = filter.filter(obj, compiler, "'var1'");
    assertEquals("var1", res);
  }

  @Test(expected = InterpretException.class)
  public void test6() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var5");
    Object res = filter.filter(obj, compiler);
    assertEquals("var1", res);
  }

  @Test
  public void test7() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "var2");
    assertEquals("hello", res);
  }

  @Test
  public void test8() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var6");
    Object res = filter.filter(obj, compiler, "var2");
    assertEquals("hello", res);
  }

  @Test
  public void test9() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "var2");
    assertEquals("hello", res);
  }

  @Test
  public void test10() throws CarrotException {
    Object obj = compiler.fetchRuntimeScope("var8");
    Object res = filter.filter(obj, compiler, "var2");
    assertEquals("hello", res);
  }

}
