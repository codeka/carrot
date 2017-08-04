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

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for {@link Function}.
 */
@RunWith(JUnit4.class)
public class FunctionTest {
  @Test
  public void testMethodNotFound() {
    Function f = new Function.Builder(new Identifier(new Token(TokenType.IDENTIFIER, "foo"))).build();
    try {
      f.evaluate(new Object(), new Configuration(), new Scope(new HashMap<String, Object>()));
      fail("CarrotException expected.");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("No matching method 'foo' found on class java.lang.Object, candidates: []");
    }
  }

  @Test
  public void testWrongNumberOfParameters() {
    Function f = new Function.Builder(new Identifier(new Token(TokenType.IDENTIFIER, "foo")))
        .addParam(parseExpression("12"))
        .build();

    try {
      f.evaluate(new FooWithTwoArgs(), new Configuration(), new Scope(new HashMap<String, Object>()));
      fail("CarrotException expected.");
    } catch (CarrotException e) {
      assertThat(e.getMessage())
          .isEqualTo("No matching method 'foo' found on class au.com.codeka.carrot.expr.FunctionTest$FooWithTwoArgs,"
              + " candidates: [public int au.com.codeka.carrot.expr.FunctionTest$FooWithTwoArgs.foo(int,int)]");
    }
  }

  @Test
  public void testInconvertibleTypes() {
    Function f = new Function.Builder(new Identifier(new Token(TokenType.IDENTIFIER, "foo")))
        .addParam(parseExpression("\"Hello\""))
        .addParam(parseExpression("\"World\""))
        .build();

    try {
      f.evaluate(new FooWithTwoArgs(), new Configuration(), new Scope(new HashMap<String, Object>()));
      fail("CarrotException expected.");
    } catch (CarrotException e) {
      assertThat(e.getMessage())
          .isEqualTo("No matching method 'foo' found on class au.com.codeka.carrot.expr.FunctionTest$FooWithTwoArgs,"
              + " candidates: [public int au.com.codeka.carrot.expr.FunctionTest$FooWithTwoArgs.foo(int,int)]");
    }
  }

  private static class FooWithTwoArgs {
    public int foo(int a, int b) {
      return a + b;
    }
  }

  private Expression parseExpression(String expr) {
    try {
      return new StatementParser(
          new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(expr)))).parseExpression();
    } catch (CarrotException e) {
      fail(e.getMessage());
      throw new RuntimeException(); // Won't get here.
    }
  }
}
