package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.bindings.EmptyBindings;
import au.com.codeka.carrot.resource.ResourcePointer;
import au.com.codeka.carrot.util.LineReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for {@link Function}.
 */
@RunWith(JUnit4.class)
public class FunctionTest {
  @Test
  public void testMethodNotFound() {
    Function f = new Function(new Identifier(new Token(TokenType.IDENTIFIER, "foo")), new EmptyTerm());
    try {
      f.evaluate(new Object(), new Configuration(), new Scope(new EmptyBindings()));
      fail("CarrotException expected.");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("No matching method 'foo' found on class java.lang.Object, candidates: []");
    }
  }

  @Test
  public void testWrongNumberOfParameters() {
    Function f = new Function(new Identifier(new Token(TokenType.IDENTIFIER, "foo")), parseExpression("12"));

    try {
      f.evaluate(new FooWithTwoArgs(), new Configuration(), new Scope(new EmptyBindings()));
      fail("CarrotException expected.");
    } catch (CarrotException e) {
      assertThat(e.getMessage())
          .isEqualTo("No matching method 'foo' found on class au.com.codeka.carrot.expr.FunctionTest$FooWithTwoArgs,"
              + " candidates: [public int au.com.codeka.carrot.expr.FunctionTest$FooWithTwoArgs.foo(int,int)]");
    }
  }

  @Test
  public void testInconvertibleTypes() {
    Function f = new Function(new Identifier(new Token(TokenType.IDENTIFIER, "foo")),parseExpression("\"Hello\", \"World\""));

    try {
      f.evaluate(new FooWithTwoArgs(), new Configuration(), new Scope(new EmptyBindings()));
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

  private Term parseExpression(String expr) {
    try {
      return new StatementParser(
          new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(expr)))).parseTermsIterable();
    } catch (CarrotException e) {
      fail(e.getMessage());
      throw new RuntimeException(); // Won't get here.
    }
  }
}
