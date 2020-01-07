package au.com.codeka.carrot;

import au.com.codeka.carrot.bindings.*;
import au.com.codeka.carrot.resource.MemoryResourceLocator;
import au.com.codeka.carrot.tmpl.Node;
import com.google.common.collect.ImmutableMap;
import org.dmfs.iterables.ArrayIterable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.Nullable;
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
    assertThat(
        render(
            "{% for a in \"foo\", \"bar\", \"baz\" %}{{ a }}{{ !loop.last && \", \" || \"\" }}{% end %}",
            new EmptyBindings()))
        .isEqualTo("foo, bar, baz");
  }

  @Test
  public void testOperatorPrecdence() {
    assertThat(render("{{ 1 + 1 * 2 }}", new EmptyBindings())).isEqualTo("3");
    assertThat(render("{{ 1 + 2 * 4 + 10 }}", new EmptyBindings())).isEqualTo("19");
    assertThat(render("{{ 1 + 1 + 1 * 2 }}", new EmptyBindings())).isEqualTo("4");
    assertThat(render("{{ (1 + 1) * 2 }}", new EmptyBindings())).isEqualTo("4");
    assertThat(render("{{ 2 * 2 + 2 }}", new EmptyBindings())).isEqualTo("6");
    assertThat(render("{{ 2 * (2 + 2) }}", new EmptyBindings())).isEqualTo("8");
  }

  @Test
  public void testOperatorAssociativity() {
    assertThat(render("{{ 1200 / 20 / 5 }}", new EmptyBindings())).isEqualTo("12");
    assertThat(render("{{ 10 - 5 - 3 }}", new EmptyBindings())).isEqualTo("2");
  }

  @Test
  public void testIterationAccess() {
    assertThat(
        render(
            "{{ iterable[2] }}",
            new SingletonBindings("iterable", new ArrayIterable<>("a", "b", "c", "d"))))
        .isEqualTo("c");
    assertThat(
        render(
            "{% set values = \"a\", \"b\", \"c\", \"d\" %}{{ values[2] }}",
            new EmptyBindings()))
        .isEqualTo("c");
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

    assertThat(render("{{ foo and \"a\" or \"b\"}}", new SingletonBindings("foo", true))).isEqualTo("a");
    assertThat(render("{{ foo and \"a\" or \"b\"}}", new SingletonBindings("foo", false))).isEqualTo("b");
    assertThat(render("{{ (foo and \"a\") or \"b\"}}", new SingletonBindings("foo", false))).isEqualTo("b");
    assertThat(render("{{ foo and \"a\"}}", new SingletonBindings("foo", true))).isEqualTo("a");
    assertThat(render("{{ foo and \"a\"}}", new SingletonBindings("foo", false))).isEqualTo("false");
    assertThat(render("{{ foo or \"a\"}}", new SingletonBindings("foo", true))).isEqualTo("true");
    assertThat(render("{{ foo or \"a\"}}", new SingletonBindings("foo", false))).isEqualTo("a");
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
    assertThat(render("{{ \"Some <b>HTML</b> here\" }}", new EmptyBindings()))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");

    assertThat(
        render(
            new Configuration.Builder().setAutoEscape(false),
            "{{ \"Some <b>HTML</b> here\" }}",
            new EmptyBindings()))
        .isEqualTo("Some <b>HTML</b> here");

    assertThat(
        render(
            new Configuration.Builder().setAutoEscape(true),
            "{{ \"Some <b>HTML</b> here\" }}",
            new EmptyBindings()))
        .isEqualTo("Some &lt;b&gt;HTML&lt;/b&gt; here");
  }

  @Test
  public void testNestedBindings() {
    assertThat(
        render(
            "foo{{ $map.foo.bar[$map.baz] }}",
            new Composite(
                new SingletonBindings("$map", new MapBindings(
                    ImmutableMap.of(
                        "foo",
                        new Object() {
                          public Map<String, String> getBar() {
                            return ImmutableMap.of("hello", "World");
                          }
                        },
                        "baz",
                        "hello"))))))
        .isEqualTo("fooWorld");
  }

  @Test
  public void testJsonObjectIterable() {
    assertThat(
        render(
            "{% for item in $json %}{{ item.key }} -> {{ item.value }}\n{% end %}",
            new SingletonBindings(
                "$json",
                new JsonObjectBindings(
                    new JSONObject("{ \"key1\": \"a\", \"key2\": 2, \"key3\": true, \"key4\": null }")))))
        .isEqualTo("key1 -> a\nkey2 -> 2\nkey3 -> true\nkey4 -> \n");
  }


  @Test
  public void testJsonObjectIterableAlternativeNotation() {
    assertThat(
        render(
            "{% for key, value in $json %}{{ key }} -> {{ value }}\n{% end %}",
            new SingletonBindings(
                "$json",
                new JsonObjectBindings(
                    new JSONObject("{\"key1\": \"a\", \"key2\": 2, \"key3\": true, \"key4\": null }")))))
        .isEqualTo("key1 -> a\nkey2 -> 2\nkey3 -> true\nkey4 -> \n");
  }

  @Test
  public void testNestedJsonObjectIterable() {
    assertThat(render(
        "{% for item in $json.map %}{{ item.key }} -> {{ $json.map[item.key] }}\n{% end %}",
        new SingletonBindings(
            "$json",
            new JsonObjectBindings(
                new JSONObject(
                    "{ \"map\": { \"key1\": \"a\", \"key2\": 2, \"key3\": true, \"key4\": null } }")))))
        .isEqualTo("key1 -> a\nkey2 -> 2\nkey3 -> true\nkey4 -> \n");
  }

  @Test
  public void testJsonArrayIterable() {
    assertThat(render("{% for value in $json %}{{ value }}{% end %}",
        new SingletonBindings("$json", new JsonArrayBindings(new JSONArray("[ \"a\", 2, true, null]")))))
        .isEqualTo("a2true");
  }

  @Test
  public void testSetValueAndStringConcat() {
    assertThat(render("{% set a = \"aaa\" %}{% set b = \"bbb\" %}{% set c = a + b %}{{ c }}", null))
        .isEqualTo("aaabbb");

  }


  private String render(String template, @Nullable Bindings bindings) {
    return render(new Configuration.Builder(), template, bindings);
  }

  private String render(Configuration.Builder configBuilder, String template, @Nullable Bindings bindings) {
    CarrotEngine engine = new CarrotEngine(configBuilder
        .setLogger(new Configuration.Logger() {
          @Override
          public void print(int level, String msg) {
            System.err.println(msg);
          }
        })
        .setResourceLocator(new MemoryResourceLocator.Builder().add("index.html", template))
        .build());
    try {
      return engine.process("index.html", bindings);
    } catch (CarrotException e) {
      throw new RuntimeException(e);
    }
  }
}
