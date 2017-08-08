package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static au.com.codeka.carrot.util.RenderHelper.render;
import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link IfTag},
 */
@RunWith(JUnit4.class)
public class IfTagTest {
  public enum SomeEnum {
    ONE,
    TWO,
  }

  @Test
  public void testEnumEquality() throws CarrotException {
    assertThat(render(
              "{% if someEnum.toString() == \"TWO\" %}"
            + "SomeEnum is TWO"
            + "{% end %}",
        "someEnum", SomeEnum.TWO))
        .isEqualTo("SomeEnum is TWO");

    assertThat(render(
        "{% if someEnum.toString() == \"TWO\" %}"
            + "SomeEnum is not TWO"
            + "{% end %}",
        "someEnum", SomeEnum.ONE))
        .isEqualTo("");
  }

  @Test
  public void testIntegerValues() throws CarrotException {
    assertThat(render("{% if foo < 10 %}yes{% end %}", "foo", 9)).isEqualTo("yes");
    assertThat(render("{% if foo < 10 %}yes{% end %}", "foo", 10)).isEqualTo("");
    assertThat(render("{% if foo < 10 %}yes{% end %}", "foo", 11)).isEqualTo("");
    assertThat(render("{% if foo > 10 %}yes{% end %}", "foo", 9)).isEqualTo("");
    assertThat(render("{% if foo > 10 %}yes{% end %}", "foo", 10)).isEqualTo("");
    assertThat(render("{% if foo > 10 %}yes{% end %}", "foo", 11)).isEqualTo("yes");
    assertThat(render("{% if foo <= 10 %}yes{% end %}", "foo", 9)).isEqualTo("yes");
    assertThat(render("{% if foo <= 10 %}yes{% end %}", "foo", 10)).isEqualTo("yes");
    assertThat(render("{% if foo <= 10 %}yes{% end %}", "foo", 11)).isEqualTo("");
    assertThat(render("{% if foo >= 10 %}yes{% end %}", "foo", 9)).isEqualTo("");
    assertThat(render("{% if foo >= 10 %}yes{% end %}", "foo", 10)).isEqualTo("yes");
    assertThat(render("{% if foo >= 10 %}yes{% end %}", "foo", 11)).isEqualTo("yes");

    assertThat(render("{% if foo <= 10 %}yes{% end %}", "foo", "10")).isEqualTo("yes");
  }

  @Test
  public void testLongValues() throws CarrotException {
    assertThat(render("{% if foo < 10000000000000 %}yes{% end %}", "foo", 9999999999999L)).isEqualTo("yes");
    assertThat(render("{% if foo < 10000000000000 %}yes{% end %}", "foo", 10000000000000L)).isEqualTo("");
    assertThat(render("{% if foo < 10000000000000 %}yes{% end %}", "foo", 10000000000001L)).isEqualTo("");
    assertThat(render("{% if foo > 10000000000000 %}yes{% end %}", "foo", 9999999999999L)).isEqualTo("");
    assertThat(render("{% if foo > 10000000000000 %}yes{% end %}", "foo", 10000000000000L)).isEqualTo("");
    assertThat(render("{% if foo > 10000000000000 %}yes{% end %}", "foo", 10000000000001L)).isEqualTo("yes");
    assertThat(render("{% if foo <= 10000000000000 %}yes{% end %}", "foo", 9999999999999L)).isEqualTo("yes");
    assertThat(render("{% if foo <= 10000000000000 %}yes{% end %}", "foo", 10000000000000L)).isEqualTo("yes");
    assertThat(render("{% if foo <= 10000000000000 %}yes{% end %}", "foo", 10000000000001L)).isEqualTo("");
    assertThat(render("{% if foo >= 10000000000000 %}yes{% end %}", "foo", 9999999999999L)).isEqualTo("");
    assertThat(render("{% if foo >= 10000000000000 %}yes{% end %}", "foo", 10000000000000L)).isEqualTo("yes");
    assertThat(render("{% if foo >= 10000000000000 %}yes{% end %}", "foo", 10000000000001L)).isEqualTo("yes");

    assertThat(render("{% if foo >= 10000000000000 %}yes{% end %}", "foo", "10000000000000")).isEqualTo("yes");
  }

  @Test
  public void testFloatValues() throws CarrotException {
    assertThat(render("{% if foo < 1.2345 %}yes{% end %}", "foo", 1.2344f)).isEqualTo("yes");
    assertThat(render("{% if foo < 1.2345 %}yes{% end %}", "foo", 1.2345f)).isEqualTo("");
    assertThat(render("{% if foo < 1.2345 %}yes{% end %}", "foo", 1.2346f)).isEqualTo("");
    assertThat(render("{% if foo > 1.2345 %}yes{% end %}", "foo", 1.2344f)).isEqualTo("");
    assertThat(render("{% if foo > 1.234 %}yes{% end %}", "foo", 1.234f)).isEqualTo("");
    assertThat(render("{% if foo > 1.2345 %}yes{% end %}", "foo", 1.2346f)).isEqualTo("yes");
    assertThat(render("{% if foo <= 1.2345 %}yes{% end %}", "foo", 1.2344f)).isEqualTo("yes");
    assertThat(render("{% if foo <= 1.234 %}yes{% end %}", "foo", 1.234f)).isEqualTo("yes");
    assertThat(render("{% if foo <= 1.2345 %}yes{% end %}", "foo", 1.2346f)).isEqualTo("");
    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", 1.2344f)).isEqualTo("");
    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", 1.2345f)).isEqualTo("yes");
    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", 1.2346f)).isEqualTo("yes");

    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", "1.2345")).isEqualTo("yes");
  }

  @Test
  public void testDoubleValues() throws CarrotException {
    assertThat(render("{% if foo < 1.2345 %}yes{% end %}", "foo", 1.2344)).isEqualTo("yes");
    assertThat(render("{% if foo < 1.2345 %}yes{% end %}", "foo", 1.2345)).isEqualTo("");
    assertThat(render("{% if foo < 1.2345 %}yes{% end %}", "foo", 1.2346)).isEqualTo("");
    assertThat(render("{% if foo > 1.2345 %}yes{% end %}", "foo", 1.2344)).isEqualTo("");
    assertThat(render("{% if foo > 1.2345 %}yes{% end %}", "foo", 1.2345)).isEqualTo("");
    assertThat(render("{% if foo > 1.2345 %}yes{% end %}", "foo", 1.2346)).isEqualTo("yes");
    assertThat(render("{% if foo <= 1.2345 %}yes{% end %}", "foo", 1.2344)).isEqualTo("yes");
    assertThat(render("{% if foo <= 1.2345 %}yes{% end %}", "foo", 1.2345)).isEqualTo("yes");
    assertThat(render("{% if foo <= 1.2345 %}yes{% end %}", "foo", 1.2346)).isEqualTo("");
    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", 1.2344)).isEqualTo("");
    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", 1.2345)).isEqualTo("yes");
    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", 1.2346)).isEqualTo("yes");

    assertThat(render("{% if foo >= 1.2345 %}yes{% end %}", "foo", "1.2345")).isEqualTo("yes");
  }

  @Test
  public void testDifferentIntegerTypesEquality() throws CarrotException {
    assertThat(render("{% if foo == bar %}yes{% end %}", "foo", 123, "bar", 123L)).isEqualTo("yes");
    assertThat(render("{% if foo == bar %}yes{% end %}", "foo", 123L, "bar", 123)).isEqualTo("yes");
    assertThat(render("{% if foo == \"123\" %}yes{% end %}", "foo", 123)).isEqualTo("yes");
    assertThat(render("{% if foo == 123 %}yes{% end %}", "foo", "123")).isEqualTo("yes");

    assertThat(render("{% if foo != bar %}yes{% end %}", "foo", 123, "bar", 123L)).isEqualTo("");
    assertThat(render("{% if foo != bar %}yes{% end %}", "foo", 123L, "bar", 123)).isEqualTo("");
    assertThat(render("{% if foo != \"123\" %}yes{% end %}", "foo", 123)).isEqualTo("");
    assertThat(render("{% if foo != 123 %}yes{% end %}", "foo", "123")).isEqualTo("");
  }

  @Test
  public void testIfNot() throws CarrotException {
    assertThat(render("{% if !foo %}yes{% else %}no{% end %}", "foo", true)).isEqualTo("no");
    assertThat(render("{% if !foo %}yes{% else %}no{% end %}", "foo", false)).isEqualTo("yes");
  }

  @Test
  public void testNulls() throws CarrotException {
    assertThat(render("{% if foo == null %}yes{% end %}", "foo", null)).isEqualTo("yes");
    assertThat(render("{% if foo == bar %}yes{% end %}", "foo", null, "bar", null)).isEqualTo("yes");
    assertThat(render("{% if foo == bar %}yes{% end %}", "foo", new Object(), "bar", null)).isEqualTo("");
    assertThat(render("{% if foo != bar %}yes{% end %}", "foo", new Object(), "bar", null)).isEqualTo("yes");
    assertThat(render("{% if foo != null %}yes{% end %}", "foo", new Object(), "bar", null)).isEqualTo("yes");

    assertThat(render("{% if foo != null && foo.blah == null %}yes{% end %}", "foo", null)).isEqualTo("");

    assertThat(render("{% if foo != null && foo.bar == null %}yes{% end %}", "foo", new Object() {
      public Object bar = null;
    })).isEqualTo("yes");

    assertThat(render("{% if foo.bar != null && foo.bar.toString() == \"blah\" %}yes{% end %}", "foo", new Object() {
      public Object bar = new Object() {
        @Override
        public String toString() {
          return "blah";
        }
      };
    })).isEqualTo("yes");

    assertThat(render("{% if foo.bar == null %}yes{% end %}", "foo", new Object() {
      public Object bar = null;
    })).isEqualTo("yes");
  }
}
