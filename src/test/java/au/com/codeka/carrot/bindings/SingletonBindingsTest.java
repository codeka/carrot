package au.com.codeka.carrot.bindings;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * @author marten
 */
public class SingletonBindingsTest {
  @Test
  public void testResolved() throws Exception {
    assertThat(new SingletonBindings("key", "value").resolve("key"), CoreMatchers.<Object>is("value"));
    assertThat(new SingletonBindings("key", "value").resolve("otherkey"), CoreMatchers.nullValue());
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertThat(new SingletonBindings("key", "value").isEmpty(), CoreMatchers.is(false));
  }
}