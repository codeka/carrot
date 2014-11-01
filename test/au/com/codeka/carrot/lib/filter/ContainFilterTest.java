package au.com.codeka.carrot.lib.filter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.interpret.InterpretException;

public class ContainFilterTest extends ZzzBase {

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    filter = new ContainFilter();
    compiler.assignRuntimeScope("var1",
        new String[] { "abc", "def", null, "ghi", "xyz", "123", "aaa" });
    compiler.assignRuntimeScope("var2", "asdfghjkl");
    compiler.assignRuntimeScope("var3", null);
    compiler.assignRuntimeScope("var4", 234);
    compiler.assignRuntimeScope("var5", compiler);
    HashMap map = new HashMap();
    map.put("a", "aaa");
    map.put("b", "bbb");
    map.put("c", "ccc");
    map.put("d", null);
    compiler.assignRuntimeScope("var6", map);
    ArrayList al = new ArrayList();
    al.add("ddd");
    al.add("ccc");
    al.add(234);
    al.add(map);
    al.add(al);
    al.add("abc");
    al.add(null);
    al.add(true);
    compiler.assignRuntimeScope("var7", al);
    compiler.assignRuntimeScope("var8", "bcd");
  }

  @Test
  public void test1() throws InterpretException {
    Object res = filter.filter("abcd", compiler, "var8");
    assertEquals(true, res);
  }

  @Test
  public void test2() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "var6.a");
    assertEquals(true, res);
  }

  @Test
  public void test3() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "'def'");
    assertEquals(true, res);
  }

  @Test
  public void test4() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "var6");
    assertEquals(true, res);
  }

  @Test
  public void test5() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "var6.c");
    assertEquals(true, res);
  }

  @Test
  public void test6() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "var4");
    assertEquals(true, res);
  }

  @Test
  public void test7() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "var3");
    assertEquals(true, res);
  }

  @Test
  public void test8() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "var3");
    assertEquals(true, res);
  }

  @Test
  public void test9() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var2");
    Object res = filter.filter(obj, compiler, "'dfgh\"");
    assertEquals(true, res);
  }

  @Test(expected = InterpretException.class)
  public void test10() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var5");
    Object res = filter.filter(obj, compiler, "'dfgh\"");
    assertEquals(true, res);
  }

  @Test
  public void test11() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var6");
    Object res = filter.filter(obj, compiler, "'aaa'");
    assertEquals(true, res);
  }

  @Test
  public void test12() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var6");
    Object res = filter.filter(obj, compiler, "tv");
    assertEquals(true, res);
  }

  @Test
  public void test13() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "'234'");
    assertEquals(true, res);
  }

  @Test
  public void test16() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "234");
    assertEquals(true, res);
  }

  @Test
  public void test14() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "var7");
    assertEquals(true, res);
  }

  @Test
  public void test15() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var7");
    Object res = filter.filter(obj, compiler, "'TRUe'");
    assertEquals(true, res);
  }

  @Test
  public void testGetName() {
    assertEquals("contain", filter.getName());
  }
}
