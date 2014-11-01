package au.com.codeka.carrot.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ObjectIteratorTest {

  private Object items = null;
  private ForLoop<Object> loop = null;

  @Test
  public void test1() {
    loop = ObjectIterator.getLoop(items);
    assertEquals(false, loop.hasNext());
    assertEquals(0, loop.getLength());
  }

  @Test
  public void test2() {
    items = "hello";
    loop = ObjectIterator.getLoop(items);
    assertEquals(1, loop.getLength());
  }

  @Test
  public void test3() {
    items = 2;
    loop = ObjectIterator.getLoop(items);
    assertEquals(1, loop.getLength());
  }

  @Test
  public void test4() {
    items = new Integer[] { 7, 8, 9 };
    loop = ObjectIterator.getLoop(items);
    assertEquals(3, loop.getLength());
    loop.next();
    assertEquals(8, loop.next());
  }

  @Test
  public void test5() {
    items = new String[] { "jan", "god" };
    loop = ObjectIterator.getLoop(items);
    assertEquals(2, loop.getLength());
    assertEquals("jan", loop.next());
    assertEquals("god", loop.next());
  }

  @Test
  public void test6() {
    List<String> items = new ArrayList<String>();
    items.add("hello");
    items.add("world");
    items.add("carrot");
    items.add("codeka");
    loop = ObjectIterator.getLoop(items);
    assertEquals(4, loop.getLength());
    assertEquals("hello", loop.next());
  }

  @Test
  public void test7() {
    Map<Object, Object> items = new HashMap<Object, Object>();
    items.put("ok", 1);
    items.put(1, "ok");
    items.put(2, 2);
    items.put("ko", "ko");
    items.put("test", new ObjectIteratorTest());
    loop = ObjectIterator.getLoop(items);
    assertEquals(5, loop.getLength());
  }
}
