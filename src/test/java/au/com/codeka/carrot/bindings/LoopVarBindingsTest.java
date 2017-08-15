package au.com.codeka.carrot.bindings;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

/**
 * @author marten
 */
public class LoopVarBindingsTest {
  @Test
  public void testResolve() throws Exception {
    assertThat(new LoopVarBindings(3, 1).resolve("index"), CoreMatchers.<Object>is(1));
    assertThat(new LoopVarBindings(3, 1).resolve("revindex"), CoreMatchers.<Object>is(1));
    assertThat(new LoopVarBindings(3, 1).resolve("first"), CoreMatchers.<Object>is(false));
    assertThat(new LoopVarBindings(3, 1).resolve("last"), CoreMatchers.<Object>is(false));
    assertThat(new LoopVarBindings(3, 1).resolve("length"), CoreMatchers.<Object>is(3));


    assertThat(new LoopVarBindings(3, 0).resolve("index"), CoreMatchers.<Object>is(0));
    assertThat(new LoopVarBindings(3, 0).resolve("revindex"), CoreMatchers.<Object>is(2));
    assertThat(new LoopVarBindings(3, 0).resolve("first"), CoreMatchers.<Object>is(true));
    assertThat(new LoopVarBindings(3, 0).resolve("last"), CoreMatchers.<Object>is(false));
    assertThat(new LoopVarBindings(3, 0).resolve("length"), CoreMatchers.<Object>is(3));

    assertThat(new LoopVarBindings(3, 2).resolve("index"), CoreMatchers.<Object>is(2));
    assertThat(new LoopVarBindings(3, 2).resolve("revindex"), CoreMatchers.<Object>is(0));
    assertThat(new LoopVarBindings(3, 2).resolve("first"), CoreMatchers.<Object>is(false));
    assertThat(new LoopVarBindings(3, 2).resolve("last"), CoreMatchers.<Object>is(true));
    assertThat(new LoopVarBindings(3, 2).resolve("length"), CoreMatchers.<Object>is(3));


    assertThat(new LoopVarBindings(3, 2).resolve("xyz"), CoreMatchers.nullValue());
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertThat(new LoopVarBindings(1, 0).isEmpty(), CoreMatchers.is(false));
    assertThat(new LoopVarBindings(3, 1).isEmpty(), CoreMatchers.is(false));
  }

}