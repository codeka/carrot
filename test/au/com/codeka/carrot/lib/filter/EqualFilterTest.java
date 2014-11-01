package au.com.codeka.carrot.lib.filter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.interpret.InterpretException;

public class EqualFilterTest extends ZzzBase {

  @SuppressWarnings("unchecked")
  @Before
  public void setUp() throws Exception {
    filter = new EqualFilter();
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
    Object res = filter.filter("bcd", compiler, "var8");
    assertEquals(true, res);
  }

  @Test
  public void test2() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "'234'");
    assertEquals(true, res);
  }

  @Test
  public void test3() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var3");
    Object res = filter.filter(obj, compiler, "'234'");
    assertEquals(false, res);
  }

  @Test
  public void test4() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var3");
    Object res = filter.filter(obj, compiler, "'234'");
    assertEquals(false, res);
  }

  @Test
  public void test5() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var5");
    Object res = filter.filter(obj, compiler, "var5");
    assertEquals(true, res);
  }

  @Test
  public void testGetName() {
    assertEquals("equal", filter.getName());
  }

}
