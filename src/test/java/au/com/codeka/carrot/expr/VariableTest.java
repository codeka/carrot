package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.resource.ResourcePointer;
import au.com.codeka.carrot.util.LineReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link Variable}.
 */
@RunWith(JUnit4.class)
public class VariableTest {
  @Test
  public void testSimpleVariable() throws CarrotException {
    Variable var = parse("foo");
    Scope scope = createScope("foo", 1234L);
    Object value = var.evaluate(new Configuration(), scope);
    assertThat(value).isEqualTo(1234L);
  }

  @Test
  public void testFunctionNoArgs() throws CarrotException {
    Variable var = parse("foo.bar()");

    Scope scope = createScope("foo", new Object() {
      public String bar() {
        return "Hello World";
      }
    });
    Object value = var.evaluate(new Configuration(), scope);
    assertThat(value).isEqualTo("Hello World");
  }

  @Test
  public void testFunctionTwoLiteralArgs() throws CarrotException {
    Variable var = parse("foo.add(12, 34)");

    Scope scope = createScope("foo", new Object() {
      public int add(int a, int b) {
        return a + b;
      }
    });
    Object value = var.evaluate(createConfig(), scope);
    assertThat(value).isEqualTo(12 + 34);
  }

  @Test
  public void testFunctionTwoVariableArgs() throws CarrotException {
    Variable var = parse("foo.append(bar, baz)");

    Scope scope = createScope(
        "foo", new Object() {
              public String append(String a, String b) {
                return a + " " + b;
              }
            },
        "bar", "Hello",
        "baz", "World");
    Object value = var.evaluate(createConfig(), scope);
    assertThat(value).isEqualTo("Hello World");
  }

  private Configuration createConfig() {
    Configuration config = new Configuration();
    config.setLogger((level, msg) -> System.err.println(msg));
    return config;
  }

  private Scope createScope(Object... values) {
    Map<String, Object> map = new HashMap<>();
    for (int i = 0; i < values.length; i += 2) {
      map.put(values[i].toString(), values[i+1]);
    }
    return new Scope(map);
  }

  private Variable parse(String expr) throws CarrotException {
    return new StatementParser(
        new Tokenizer(
            new LineReader(
                new ResourcePointer(null),
                new StringReader(expr))))
        .parseVariable();
  }
}
