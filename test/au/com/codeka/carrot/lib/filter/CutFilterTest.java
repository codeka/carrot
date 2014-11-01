package au.com.codeka.carrot.lib.filter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.interpret.InterpretException;

public class CutFilterTest extends ZzzBase {

  @Before
  public void setUp() throws Exception {
    filter = new CutFilter();
    compiler.assignRuntimeScope("var1", "a.bc,ef\\,g[]*.~^/$abce ");
    compiler.assignRuntimeScope("var2", "aaaabbbbccccddddfffff");
    compiler.assignRuntimeScope("var3", "1234123412341234	 	1234");
    compiler.assignRuntimeScope("var4", "&absp;\"<abc>''adfl'\"\"</abc>&gt;&amp;");
  }

  @Test
  public void test1() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "'a'");
    assertEquals(".bc,ef\\,g[]*.~^/$bce ", res);
  }

  @Test
  public void test2() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "a");
    assertEquals(".bc,ef\\,g[]*.~^/$bce ", res);
  }

  @Test
  public void test3() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "'.'");
    assertEquals("abc,ef\\,g[]*~^/$abce ", res);
  }

  @Test
  public void test9() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "'^'");
    assertEquals("a.bc,ef\\,g[]*.~/$abce ", res);
  }

  @Test
  public void test4() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var1");
    Object res = filter.filter(obj, compiler, "'\\'");
    assertEquals("a.bc,ef,g[]*.~^/$abce ", res);
  }

  @Test
  public void test5() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var2");
    Object res = filter.filter(obj, compiler, "'c'");
    assertEquals("aaaabbbbddddfffff", res);
  }

  @Test
  public void test6() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var3");
    Object res = filter.filter(obj, compiler, "2");
    assertEquals("134134134134	 	134", res);
  }

  @Test
  public void test7() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var3");
    Object res = filter.filter(obj, compiler, "' '");
    assertEquals("1234123412341234		1234", res);
  }

  @Test
  public void test8() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "&");
    assertEquals("absp;\"<abc>''adfl'\"\"</abc>gt;amp;", res);
  }

  @Test
  public void test10() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "'''");
    assertEquals("&absp;\"<abc>adfl\"\"</abc>&gt;&amp;", res);
  }

  @Test
  public void test11() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "'\"'");
    assertEquals("&absp;<abc>''adfl'</abc>&gt;&amp;", res);
  }

  @Test
  public void test12() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "'>'");
    assertEquals("&absp;\"<abc''adfl'\"\"</abc&gt;&amp;", res);
  }

  @Test
  public void test13() throws InterpretException {
    Object obj = compiler.fetchRuntimeScope("var4");
    Object res = filter.filter(obj, compiler, "'/'");
    assertEquals("&absp;\"<abc>''adfl'\"\"<abc>&gt;&amp;", res);
  }

  @Test
  public void testGetName() {
    assertEquals("cut", filter.getName());
  }
}
