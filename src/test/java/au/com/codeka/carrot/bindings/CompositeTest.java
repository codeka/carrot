package au.com.codeka.carrot.bindings;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * @author marten
 */
public class CompositeTest {

  @Test
  public void testResolved() throws Exception {
    assertThat(new Composite().resolve("key"), CoreMatchers.nullValue());
    assertThat(new Composite(new EmptyBindings()).resolve("key"), CoreMatchers.nullValue());
    assertThat(new Composite(new EmptyBindings(), new EmptyBindings()).resolve("key"), CoreMatchers.nullValue());

    assertThat(new Composite(new SingletonBindings("key", "value")).resolve("key"), CoreMatchers.<Object>is("value"));
    assertThat(new Composite(new EmptyBindings(), new SingletonBindings("key", "value")).resolve("key"), CoreMatchers.<Object>is("value"));
    assertThat(new Composite(new SingletonBindings("key1", "value1"), new SingletonBindings("key2", "value2")).resolve("key1"), CoreMatchers.<Object>is("value1"));
    assertThat(new Composite(new SingletonBindings("key1", "value1"), new SingletonBindings("key2", "value2")).resolve("key2"), CoreMatchers.<Object>is("value2"));
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertThat(new Composite().isEmpty(), CoreMatchers.is(true));
    assertThat(new Composite(new EmptyBindings()).isEmpty(), CoreMatchers.is(true));
    assertThat(new Composite(new EmptyBindings(), new EmptyBindings()).isEmpty(), CoreMatchers.is(true));

    assertThat(new Composite(new EmptyBindings(), new SingletonBindings("key", "value")).isEmpty(), CoreMatchers.is(false));
    assertThat(new Composite(new SingletonBindings("key1", "value1"), new SingletonBindings("key2", "value2")).isEmpty(), CoreMatchers.is(false));
  }

}