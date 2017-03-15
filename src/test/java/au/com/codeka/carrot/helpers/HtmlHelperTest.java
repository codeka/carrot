package au.com.codeka.carrot.helpers;

import au.com.codeka.carrot.CarrotException;
import org.junit.Test;

import static au.com.codeka.carrot.util.RenderHelper.render;
import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link HtmlHelper}.
 */
public class HtmlHelperTest {
  @Test
  public void testDefaultEscaping() throws CarrotException {
    assertThat(render("{{ \"Some <b>HTML</b> here\" }}"))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");
  }

  @Test
  public void testSafeString() throws CarrotException {
    assertThat(render("{{ html.safe(\"Some <b>HTML</b> here\") }}"))
        .isEqualTo("Some <b>HTML</b> here");
  }

  @Test
  public void testEscape() throws CarrotException {
    assertThat(render("{{ html.escape(\"Some <b>HTML</b> here\") }}"))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");

    // Double-escaping doesn't actually double-escape.
    assertThat(render("{{ html.escape(html.escape(\"Some <b>HTML</b> here\")) }}"))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");
  }
}
