package au.com.codeka.carrot.bindings;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * @author marten
 */
public class EmptyBindingsTest {
  @Test
  public void testResolved() throws Exception {
    assertThat(new EmptyBindings().resolve("abc"), CoreMatchers.nullValue());
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertThat(new EmptyBindings().isEmpty(), CoreMatchers.is(true));
  }

}