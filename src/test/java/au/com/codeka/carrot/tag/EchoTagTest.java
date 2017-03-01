package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.resource.MemoryResourceLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link EchoTag}.
 */
@RunWith(JUnit4.class)
public class EchoTagTest {
  @Test
  public void testMaths() throws CarrotException {
    assertThat(render("{{ a + 1 }}", "a", 2)).isEqualTo("3");
    assertThat(render("{{ 4 * a }}", "a", 3)).isEqualTo("12");
    assertThat(render("{{ a / b }}", "a", 10, "b", 5)).isEqualTo("2");
    assertThat(render("{{ a / b }}", "a", 10.1, "b", 5)).isEqualTo("2.02");
    assertThat(render("{{ a / (b + 1) }}", "a", 10.1, "b", 4)).isEqualTo("2.02");
  }

  @Test
  public void testFunctions() throws CarrotException {
    assertThat(render("{{ a.foo(b) }}",
        "a", new Object() {
          public Object foo(long l) {
            return l + 10;
          }
        },
        "b", 5)).isEqualTo("15");

    assertThat(render("{{ a.foo(b) }}",
        "a", new Object() {
          public Object foo(short s) {
            return s + 10;
          }
        },
        "b", 5)).isEqualTo("15");

    assertThat(render("{{ a.foo(b) }}",
        "a", new Object() {
          public Object foo(byte b) {
            return b + 11;
          }
        },
        "b", 4)).isEqualTo("15");

    assertThat(render("{{ a.foo(b) }}",
        "a", new Object() {
          public Object foo(float f) {
            return f + 11.5f;
          }
        },
        "b", 4.3)).isEqualTo("15.8");

    assertThat(render("{{ a.foo(b) }}",
        "a", new Object() {
          public Object foo(double d) {
            return d + 11.5;
          }
        },
        "b", 2)).isEqualTo("13.5");
  }

  private String render(String content, Object... bindings) throws CarrotException {
    CarrotEngine engine = new CarrotEngine();
    engine.getConfig().setLogger((level, msg) -> System.err.println(msg));

    Map<String, String> resources = new TreeMap<>();
    resources.put("index", content);
    MemoryResourceLocator resourceLocator = new MemoryResourceLocator(resources);
    engine.getConfig().setResourceLocater(resourceLocator);

    Map<String, Object> bindingsMap = new HashMap<>();
    for (int i = 0; i < bindings.length; i += 2) {
      bindingsMap.put(bindings[i].toString(), bindings[i + 1]);
    }

    return engine.process("index", bindingsMap);
  }
}
