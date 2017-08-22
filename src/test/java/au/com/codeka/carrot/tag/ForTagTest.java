package au.com.codeka.carrot.tag;

import au.com.codeka.carrot.CarrotEngine;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.bindings.EmptyBindings;
import au.com.codeka.carrot.bindings.MapBindings;
import au.com.codeka.carrot.resource.MemoryResourceLocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.annotation.Nullable;
import java.util.*;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link ForTag}.
 */
@RunWith(JUnit4.class)
public class ForTagTest {
  @Test
  public void testArrayLoop() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    context.put("values", new int[]{3, 1, 4, 1, 5});
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
  public void testArrayExpansionLoop() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    ArrayList<List<String>> values = new ArrayList<>();
    values.add(Arrays.asList("foo", "bar", "baz"));
    values.add(Arrays.asList("1", "2", "3"));
    values.add(Arrays.asList("a", "b", "c"));
    context.put("values", values);
    assertThat(render("Hello {% for x, y, z in values %} -{{ x }}/{{ y }}/{{ z }}- {% end %} World", context))
        .isEqualTo("Hello  -foo/bar/baz-  -1/2/3-  -a/b/c-  World");
  }

  @Test
  public void testIterableLoop() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    assertThat(render("Hello {% for a in 1, 2, 3 %} -{{ a }}- {% end %} World", context))
        .isEqualTo("Hello  -1-  -2-  -3-  World");
  }

  @Test
  public void testIterableLoopExpansion() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    assertThat(render("Hello {% for number, letter in (1, \"a\"), (2, \"b\"), (3, \"c\") %} {{ number }}:{{ letter }} {% end %} World", context))
        .isEqualTo("Hello  1:a  2:b  3:c  World");
  }

  @Test
  public void testSingleLoopExpansion() throws CarrotException {
    assertThat(render("Hello {% for number in 1,  %} {{ number }} {% end %} World", new HashMap<String, Object>())).isEqualTo("Hello  1  World");
    assertThat(render("Hello {% for number in 1, 2 %} {{ number }} {% end %} World", new HashMap<String, Object>())).isEqualTo("Hello  1  2  World");
    assertThat(render("Hello {% for number in 1, 2, %} {{ number }} {% end %} World", new HashMap<String, Object>())).isEqualTo("Hello  1  2  World");
  }


  @Test
  public void testMapExpansionLoop() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    Map<String, Object> values = new HashMap<>();
    // note, we can not test Map iteration with more values, because the iteration order is undefined
    values.put("foo", "a");
    context.put("values", new MapBindings(values));
    assertThat(render("Hello {% for key, val in values %} -{{ key }}:{{ val }}- {% end %} World", context))
        .isEqualTo("Hello  -foo:a-  World");
  }

  @Test
  public void testVariableExpansionLoop2() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    ArrayList<List<String>> values = new ArrayList<>();
    values.add(Arrays.asList("foo", "bar", "baz"));
    values.add(Arrays.asList("1", "2", "3"));
    values.add(Arrays.asList("a", "b", "c"));
    context.put("values", values);
    assertThat(render("Hello {% for x,y, z in values %} -{{ x }}/{{ y }}/{{ z }}- {% end %} World", context))
        .isEqualTo("Hello  -foo/bar/baz-  -1/2/3-  -a/b/c-  World");
  }

  @Test
  public void testKeyValueInMap() throws CarrotException {
    Map<String, Object> context = new HashMap<>();
    // Use a tree map here so the keys are sorted.
    Map<String, String> values = new TreeMap<>();
    values.put("foo", "bar");
    values.put("bar", "baz");
    values.put("baz", "foo");
    context.put("values", values);
    assertThat(render("Hello {% for k, v in values %} -{{ k }}/{{ v }}- {% end %} World", context))
        .isEqualTo("Hello  -bar/baz-  -baz/foo-  -foo/bar-  World");
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
    CarrotEngine engine = new CarrotEngine(new Configuration.Builder()
        .setLogger(new Configuration.Logger() {
              @Override
              public void print(int level, String msg) {
                System.err.println(msg);
              }
            })
        .setResourceLocater(new MemoryResourceLocator.Builder().add("index", content))
        .build());
    return engine.process("index", new MapBindings(bindings));
  }
}
