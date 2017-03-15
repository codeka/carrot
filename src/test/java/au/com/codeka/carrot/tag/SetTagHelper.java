package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static au.com.codeka.carrot.util.RenderHelper.render;
import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link SetTag}.
 */
@RunWith(JUnit4.class)
public class SetTagHelper {
  @Test
  public void testSimple() throws CarrotException {
    assertThat(render("{% set foo %}Hello World{% end %}foo={{ foo }}"))
        .isEqualTo("foo=Hello World");
  }

  @Test
  public void testUnescaped() throws CarrotException {
    assertThat(render("{% set foo %}Hello <b>World</b>{% end %}foo={{ html.safe(foo) }}"))
        .isEqualTo("foo=Hello <b>World</b>");
  }
}