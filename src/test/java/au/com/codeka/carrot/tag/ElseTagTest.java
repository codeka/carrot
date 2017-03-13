package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static au.com.codeka.carrot.util.RenderHelper.render;
import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link ElseTag}.
 */
@RunWith(JUnit4.class)
public class ElseTagTest {
  @Test
  public void testBasicIfElse() throws CarrotException {
    assertThat(render("{% if foo %}foo is true{% else %}foo is false{% end %}", "foo", false))
        .isEqualTo("foo is false");
  }

  @Test
  public void testIfElseIfElse() throws CarrotException {
    assertThat(render(
              "{% if foo %}"
            + "foo is true"
            + "{% else if bar %}"
            + "bar is true"
            + "{% else %}"
            + "neither is true"
            + "{% end %}",
        "foo", true, "bar", false))
        .isEqualTo("foo is true");

    assertThat(render(
        "{% if foo %}"
            + "foo is true"
            + "{% else if bar %}"
            + "bar is true"
            + "{% else %}"
            + "neither is true"
            + "{% end %}",
        "foo", false, "bar", true))
        .isEqualTo("bar is true");

    assertThat(render(
        "{% if foo %}"
            + "foo is true"
            + "{% else if bar %}"
            + "bar is true"
            + "{% else %}"
            + "neither is true"
            + "{% end %}",
        "foo", false, "bar", false))
        .isEqualTo("neither is true");
  }
}
