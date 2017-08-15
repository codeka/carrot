package au.com.codeka.carrot.bindings;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThat;

/**
 * @author marten
 */
public class MapBindingsTest {
  @Test
  public void testResolvedEmpty() throws Exception {
    assertThat(new MapBindings(new HashMap<String, Object>()).resolve("key"), CoreMatchers.nullValue());
  }

  @Test
  public void testResolved() throws Exception {
    Map<String, Object> bindings = new HashMap<>();
    bindings.put("key1", "value");
    bindings.put("key2", 1);
    bindings.put("key3", true);

    assertThat(new MapBindings(bindings).resolve("key1"), CoreMatchers.<Object>is("value"));
    assertThat(new MapBindings(bindings).resolve("key2"), CoreMatchers.<Object>is(1));
    assertThat(new MapBindings(bindings).resolve("key3"), CoreMatchers.<Object>is(true));
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertThat(new MapBindings(new HashMap<String, Object>()).isEmpty(), CoreMatchers.is(true));
  }

  @Test
  public void testIsEmptyNotEmpty() throws Exception {
    Map<String, Object> bindings = new HashMap<>();
    bindings.put("key1", "value");
    assertThat(new MapBindings(bindings).isEmpty(), CoreMatchers.is(false));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testRemove() throws Exception {
    new MapBindings(new HashMap<String, Object>()).iterator().remove();
  }
}