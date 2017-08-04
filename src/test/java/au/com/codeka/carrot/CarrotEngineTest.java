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
import java.util.HashMap;
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
    assertThat(render("foo{% if a == 0 %}bar{% end %}baz", ImmutableMap.<String, Object>of("a", 0L))).isEqualTo("foobarbaz");
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

  @Test
  public void testAutoEscape() {
    CarrotEngine engine = createEngine();
    assertThat(render(engine, "{{ \"Some <b>HTML</b> here\" }}", new HashMap<String, Object>()))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");

    engine.getConfig().setAutoEscape(false);
    assertThat(render(engine, "{{ \"Some <b>HTML</b> here\" }}", new HashMap<String, Object>()))
        .isEqualTo("Some <b>HTML</b> here");

    engine.getConfig().setAutoEscape(true);
    assertThat(render(engine, "{{ \"Some <b>HTML</b> here\" }}", new HashMap<String, Object>()))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");
  }

  private CarrotEngine createEngine() {
    CarrotEngine engine = new CarrotEngine();
    engine.getConfig().setLogger(new Configuration.Logger()
    {
      @Override
      public void print(int level, String msg)
      {
        System.err.println(msg);
      }
    });
    return engine;
  }

  private String render(String template, @Nullable Map<String, Object> bindings) {
    return render(createEngine(), template, bindings);
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
