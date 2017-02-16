package au.com.codeka.carrot.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import au.com.codeka.carrot.base.CarrotException;

public class VariableTest {

  @SuppressWarnings("deprecation") // Date.getDate() is deprecated, we don't care.
  @Test
  public void simpleGetValue() throws CarrotException {
    Date obj = new Date();
    Variable var = new Variable("now.Date");
    Object res = var.resolve(obj);
    assertEquals(new Date().getDate(), res);
  }

  @Test
  public void mapLongKeys() throws CarrotException {
    Map<Long, String> map = new HashMap<>();
    map.put(37L, "Hello");
    map.put(747466383832721L, "World");

    assertEquals("Hello", new Variable("map.37").resolve(map));
    assertEquals("World", new Variable("map[747466383832721]").resolve(map));
  }

  @Test
  public void nestedMapIntegerKeys() throws CarrotException {
    Map<String, Object> values = new HashMap<>();
    Map<Integer, String> child = new HashMap<>();
    child.put(23, "Hello");
    child.put(372783, "Stuff");
    values.put("world", child);

    assertEquals("Stuff", new Variable("data.world[372783]").resolve(values));
  }
}
