package au.com.codeka.carrot;

import au.com.codeka.carrot.bindings.*;
import au.com.codeka.carrot.resource.AbstractResourceName;
import au.com.codeka.carrot.resource.ResourceLocater;
import au.com.codeka.carrot.resource.ResourceName;
import com.google.common.collect.ImmutableMap;
import org.json.JSONArray;
import org.json.JSONObject;
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
    assertThat(render("foo{% if a == 0 %}bar{% end %}baz", new SingletonBindings("a", 0L))).isEqualTo("foobarbaz");
  }

  @Test
  public void testIterationLoopAndConditionalsTag() {
    assertThat(render("{% for a in \"foo\", \"bar\", \"baz\" %}{{ a }}{{ !loop.last && \", \" || \"\" }}{% end %}", new EmptyBindings())).isEqualTo("foo, bar, baz");
  }

  @Test
  public void testOperatorPrecdence() {
    Map<String, Object> context = ImmutableMap.of("true", (Object) true, "false", false);

    assertThat(render("{{ 1 + 1 * 2 }}", new MapBindings(context))).isEqualTo("3");
    assertThat(render("{{ 1 + 2 * 4 + 10 }}", new MapBindings(context))).isEqualTo("19");
    assertThat(render("{{ 1 + 1 + 1 * 2 }}", new MapBindings(context))).isEqualTo("4");
    assertThat(render("{{ (1 + 1) * 2 }}", new MapBindings(context))).isEqualTo("4");
    assertThat(render("{{ 2 * 2 + 2 }}", new MapBindings(context))).isEqualTo("6");
    assertThat(render("{{ 2 * (2 + 2) }}", new MapBindings(context))).isEqualTo("8");
  }


  @Test
  public void testConditionalStatements() {
    assertThat(render("{{ foo && \"a\" || \"b\"}}", new SingletonBindings("foo", true))).isEqualTo("a");

    assertThat(render("{{ foo && \"a\" || \"b\"}}", new SingletonBindings("foo", false))).isEqualTo("b");
    assertThat(render("{{ (foo && \"a\") || \"b\"}}", new SingletonBindings("foo", false))).isEqualTo("b");

    assertThat(render("{{ foo && \"a\"}}", new SingletonBindings("foo", true))).isEqualTo("a");
    assertThat(render("{{ foo && \"a\"}}", new SingletonBindings("foo", false))).isEqualTo("false");

    assertThat(render("{{ foo || \"a\"}}", new SingletonBindings("foo", true))).isEqualTo("true");
    assertThat(render("{{ foo || \"a\"}}", new SingletonBindings("foo", false))).isEqualTo("a");
  }

  @Test
  public void testEchoTag() {
    assertThat(render("foo{{ foo.bar[baz] }}", new MapBindings(ImmutableMap.of(
        "foo", new Object() {
          public Map<String, String> getBar() {
            return ImmutableMap.of("hello", "World");
          }
        },
        "baz", "hello")))).isEqualTo("fooWorld");
  }

  @Test
  public void testAutoEscape() {
    CarrotEngine engine = createEngine();
    assertThat(render(engine, "{{ \"Some <b>HTML</b> here\" }}", new EmptyBindings()))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");

    engine.getConfig().setAutoEscape(false);
    assertThat(render(engine, "{{ \"Some <b>HTML</b> here\" }}", new EmptyBindings()))
        .isEqualTo("Some <b>HTML</b> here");

    engine.getConfig().setAutoEscape(true);
    assertThat(render(engine, "{{ \"Some <b>HTML</b> here\" }}", new EmptyBindings()))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");
  }

  @Test
  public void testNestedBindings() {
    assertThat(render("foo{{ $map.foo.bar[$map.baz] }}", new Composite(new SingletonBindings("$map", new MapBindings(ImmutableMap.of(
        "foo", new Object() {
          public Map<String, String> getBar() {
            return ImmutableMap.of("hello", "World");
          }
        },
        "baz", "hello")))))).isEqualTo("fooWorld");
  }


  @Test
  public void testJsonObjectIterable() {
    //language=TEXT
    assertThat(render("{% for item in $json %}{{ item.key }} -> {{ item.value }}\n{% end %}", new SingletonBindings("$json", new JsonObjectBindings(new JSONObject("{\n" +
        "  \"key1\": \"a\",\n" +
        "  \"key2\": 2,\n" +
        "  \"key3\": true,\n" +
        "  \"key4\": null\n" +
        "}"))))).isEqualTo("key1 -> a\n" +
        "key2 -> 2\n" +
        "key3 -> true\n" +
        "key4 -> \n");
  }


  @Test
  public void testJsonObjectIterableAlternativeNotation() {
    //language=TEXT
    assertThat(render("{% for key, value in $json %}{{ key }} -> {{ value }}\n{% end %}", new SingletonBindings("$json", new JsonObjectBindings(new JSONObject("{\n" +
        "  \"key1\": \"a\",\n" +
        "  \"key2\": 2,\n" +
        "  \"key3\": true,\n" +
        "  \"key4\": null\n" +
        "}"))))).isEqualTo("key1 -> a\n" +
        "key2 -> 2\n" +
        "key3 -> true\n" +
        "key4 -> \n");
  }

  @Test
  public void testNestedJsonObjectIterable() {
    assertThat(render("{% for item in $json.map %}{{ item.key }} -> {{ $json.map[item.key] }}\n{% end %}", new SingletonBindings("$json", new JsonObjectBindings(new JSONObject("{\n" +
        "  \"map\": {\n" +
        "    \"key1\": \"a\",\n" +
        "    \"key2\": 2,\n" +
        "    \"key3\": true,\n" +
        "    \"key4\": null\n" +
        "  }\n" +
        "}"))))).isEqualTo("key1 -> a\n" +
        "key2 -> 2\n" +
        "key3 -> true\n" +
        "key4 -> \n");
  }

  @Test
  public void testJsonArrayIterable() {
    //language=TEXT
    assertThat(render("{% for value in $json %}{{ value }}{% end %}",
        new SingletonBindings("$json", new JsonArrayBindings(new JSONArray("[ \"a\", 2, true, null]")))))
        .isEqualTo("a2true");
  }

  private CarrotEngine createEngine() {
    CarrotEngine engine = new CarrotEngine();
    engine.getConfig().setLogger(new Configuration.Logger() {
      @Override
      public void print(int level, String msg) {
        System.err.println(msg);
      }
    });
    return engine;
  }

  private String render(String template, @Nullable Bindings bindings) {
    return render(createEngine(), template, bindings);
  }

  private String render(CarrotEngine engine, String template, @Nullable Bindings bindings) {
    engine.getConfig().setResourceLocater(new TestResourceLocator(template));
    try {
      return engine.process("", bindings);
    } catch (CarrotException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * A test {@link ResourceLocater} that simple returns a static string content.
   */
  private class TestResourceLocator implements ResourceLocater {
    private String content;

    public TestResourceLocator(String content) {
      this.content = content;
    }

    @Override
    public ResourceName findResource(@Nullable ResourceName parent, String name) throws CarrotException {
      return new AbstractResourceName(parent, name) {
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
