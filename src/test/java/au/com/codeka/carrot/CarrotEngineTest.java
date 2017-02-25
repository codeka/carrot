package au.com.codeka.carrot;

import au.com.codeka.carrot.resource.ResourceLocater;
import au.com.codeka.carrot.resource.ResourceName;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.Nullable;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * These are basically end-to-end tests of the entire shebang.
 */
@RunWith(JUnit4.class)
public class CarrotEngineTest {
  @Test
  public void testEmptyTemplate() {
    assertThat(render("", null)).isEqualTo("");
  }

  @Test
  public void testSingleFixed() {
    assertThat(render("Hello World", null)).isEqualTo("Hello World");
  }

  @Test
  public void testIfTag() {
    assertThat(render("foo{% if a == 0 %}bar{% end %}baz", ImmutableMap.of("a", 0L))).isEqualTo("foobarbaz");
  }

  @Test
  public void testEchoTag() {
    assertThat(render("foo{{ foo.bar[baz] }}", ImmutableMap.of(
        "foo", new Object() {
          public Map<String, String> getBar() {
            return ImmutableMap.of("hello", "World");
          }
        },
        "baz", "hello"))).isEqualTo("fooWorld");
  }

  private String render(String template, @Nullable Map<String, Object> bindings) {
    CarrotEngine engine = new CarrotEngine();
    engine.getConfig().setLogger((level, msg) -> System.err.println(msg));
    return render(engine, template, bindings);
  }

  private String render(CarrotEngine engine, String template, @Nullable Map<String, Object> bindings) {
    engine.getConfig().setResourceLocater(new TestResourceLocator(template));
    try {
      return engine.process("", bindings);
    } catch (CarrotException e) {
      throw new RuntimeException(e);
    }
  }

  /** A test {@link ResourceLocater} that simple returns a static string content. */
  private class TestResourceLocator implements ResourceLocater {
    private String content;

    public TestResourceLocator(String content) {
      this.content = content;
    }

    @Override
    public ResourceName findResource(@Nullable ResourceName parent, String name) throws CarrotException {
      return new ResourceName(parent, name) {
        @Nullable
        @Override
        public ResourceName getParent() {
          return null;
        }
      };
    }

    @Override
    public ResourceName findResource(String name) throws CarrotException {
      return findResource(null, name);
    }

    @Override
    public long getModifiedTime(ResourceName resourceName) throws CarrotException {
      return 0;
    }

    @Override
    public Reader getReader(ResourceName resourceName) throws CarrotException {
      return new StringReader(content);
    }
  }
}
