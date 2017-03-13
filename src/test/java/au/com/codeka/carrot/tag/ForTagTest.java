package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.resource.MemoryResourceLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link ForTag}.
 */
@RunWith(JUnit4.class)
public class ForTagTest {
  @Test
  public void testArrayLoop() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    context.put("values", new int[] { 3, 1, 4, 1, 5 });
    assertThat(render("foo{% for n in values %}a {{ n }} b{% end %}bar", context))
        .isEqualTo("fooa 3 ba 1 ba 4 ba 1 ba 5 bbar");
  }

  @Test
  public void testArrayListLoop() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    ArrayList<String> values = new ArrayList<>();
    values.add("foo");
    values.add("bar");
    values.add("baz");
    context.put("values", values);
    assertThat(render("Hello {% for str in values %} -{{ str }}- {% end %} World", context))
        .isEqualTo("Hello  -foo-  -bar-  -baz-  World");
  }

  @Test
  public void testLoopVariables() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    ArrayList<String> values = new ArrayList<>();
    values.add("foo");
    values.add("bar");
    values.add("baz");
    context.put("values", values);
    assertThat(render("{% for str in values %}"
        + "{{ str }} {{ loop.index }} {{ loop.revindex }} {{ loop.first }} {{ loop.last }} {{ loop.length }}"
        + "{% end %}", context))
            .isEqualTo("foo 0 2 true false 3bar 1 1 false false 3baz 2 0 false true 3");
  }

  @Test
  public void testEmptyCollectionWithNoElse() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    ArrayList<String> values = new ArrayList<>();
    context.put("values", values);
    assertThat(render("{% for str in values %}"
        + "Hello {{str}} World"
        + "{% end %}", context))
        .isEqualTo("");
  }

  @Test
  public void testEmptyCollectionWithElse() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    ArrayList<String> values = new ArrayList<>();
    context.put("values", values);
    assertThat(render("{% for str in values %}"
        + "Hello {{str}} World"
        + "{% else %}"
        + "The collection is empty"
        + "{% end %}", context))
        .isEqualTo("The collection is empty");
  }

  private String render(String content, @Nullable Map<String, Object> bindings) throws CarrotException {
    CarrotEngine engine = new CarrotEngine();
    engine.getConfig().setLogger((level, msg) -> System.err.println(msg));

    Map<String, String> resources = new TreeMap<>();
    resources.put("index", content);
    MemoryResourceLocator resourceLocator = new MemoryResourceLocator(resources);
    engine.getConfig().setResourceLocater(resourceLocator);

    return engine.process("index", bindings);
  }
}
