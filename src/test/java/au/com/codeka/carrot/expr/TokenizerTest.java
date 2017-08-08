package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.resource.ResourcePointer;
import au.com.codeka.carrot.util.LineReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for {@link Tokenizer}.
 */
@RunWith(JUnit4.class)
public class TokenizerTest {
  @Test
  public void testIdentifier() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("foo bar baz");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("bar");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("baz");
  }

  @Test
  public void testParen() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("(foo) (bar)");
    assertThat(tokenizer.expect(TokenType.LPAREN)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");
    assertThat(tokenizer.expect(TokenType.RPAREN)).isNotNull();
    assertThat(tokenizer.expect(TokenType.LPAREN)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("bar");
    assertThat(tokenizer.expect(TokenType.RPAREN)).isNotNull();
  }

  @Test
  public void testBooleanOperands() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("a && b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.LOGICAL_AND)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a || b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.LOGICAL_OR)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    try {
      tokenizer = createTokenizer("a & b");
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
      assertThat(tokenizer.expect(TokenType.LOGICAL_AND)).isNotNull();
      fail("Expected CarrotException");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: a & b\n       ^\nExpected &&");
    }

    try {
      tokenizer = createTokenizer("a | b");
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
      assertThat(tokenizer.expect(TokenType.LOGICAL_OR)).isNotNull();
      fail("Expected CarrotException");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: a | b\n       ^\nExpected ||");
    }
  }

  @Test
  public void testEquality() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("a == b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.EQUALITY)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a != b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.INEQUALITY)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a ! b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.NOT)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a = b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.ASSIGNMENT)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");
  }

  @Test
  public void testGreaterLessThan() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("a < b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.LESS_THAN)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a > b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.GREATER_THAN)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a <= b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.LESS_THAN_OR_EQUAL)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a >= b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.GREATER_THAN_OR_EQUAL)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

  }

  @Test
  public void testString() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("\"Hello World\" \"This's a test\" 'So is \"this\"'");
    assertThat(tokenizer.expect(TokenType.STRING_LITERAL).getValue()).isEqualTo("Hello World");
    assertThat(tokenizer.expect(TokenType.STRING_LITERAL).getValue()).isEqualTo("This's a test");
    assertThat(tokenizer.expect(TokenType.STRING_LITERAL).getValue()).isEqualTo("So is \"this\"");
  }

  @Test
  public void testInteger() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("foo 1234 bar");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");
    assertThat(tokenizer.expect(TokenType.NUMBER_LITERAL).getValue()).isEqualTo(1234L);
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("bar");
  }

  @Test
  public void testDouble() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("foo 12.34 bar");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");
    assertThat(tokenizer.expect(TokenType.NUMBER_LITERAL).getValue()).isEqualTo(12.34);
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("bar");
  }

  @Test
  public void testUnexpectedToken() {
    try {
      Tokenizer tokenizer = createTokenizer("a + + b");
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
      assertThat(tokenizer.expect(TokenType.PLUS).getType()).isEqualTo(TokenType.PLUS);
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");
      fail("Expected CarrotException");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: a + + b\n        ^\nExpected token of type IDENTIFIER, got PLUS");
    }

    try {
      Tokenizer tokenizer = createTokenizer("a + + b");
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
      assertThat(tokenizer.expect(TokenType.PLUS).getType()).isEqualTo(TokenType.PLUS);
      assertThat(tokenizer.expect(TokenType.IDENTIFIER, TokenType.NUMBER_LITERAL, TokenType.STRING_LITERAL).getValue()).isEqualTo("b");
      fail("Expected CarrotException");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: a + + b\n        ^\nExpected token of type IDENTIFIER, NUMBER_LITERAL or STRING_LITERAL, got PLUS");
    }
  }

  private static Tokenizer createTokenizer(String str) throws CarrotException {
    return new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(str)));
  }
}
