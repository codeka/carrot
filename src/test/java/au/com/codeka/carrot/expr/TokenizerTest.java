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
  public void testIdentifierEscaping() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("foo\\.bar");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo.bar");

    tokenizer = createTokenizer("foo\\");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");

    tokenizer = createTokenizer("foo\\-value _\\.\\_");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo-value");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("_._");
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

    tokenizer = createTokenizer("a and b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.LOGICAL_AND)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a || b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.LOGICAL_OR)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("a or b");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.LOGICAL_OR)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");

    tokenizer = createTokenizer("not a");
    assertThat(tokenizer.expect(TokenType.NOT)).isNotNull();
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");

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
  public void testQuestion() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("foo ? bar : baz");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");
    assertThat(tokenizer.expect(TokenType.QUESTION).getType()).isEqualTo(TokenType.QUESTION);
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("bar");
    assertThat(tokenizer.expect(TokenType.COLON).getType()).isEqualTo(TokenType.COLON);
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("baz");

    tokenizer = createTokenizer("foo ?: bar");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");
    assertThat(tokenizer.expect(TokenType.ELVIS).getType()).isEqualTo(TokenType.ELVIS);
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
      assertThat(e.getMessage()).isEqualTo(
          "???\n1: a + + b\n        ^\nExpected token of type IDENTIFIER, got PLUS");
    }

    try {
      Tokenizer tokenizer = createTokenizer("a + + b");
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
      assertThat(tokenizer.expect(TokenType.PLUS).getType()).isEqualTo(TokenType.PLUS);
      assertThat(tokenizer.expect(
          TokenType.IDENTIFIER,
          TokenType.NUMBER_LITERAL,
          TokenType.STRING_LITERAL).getValue()).isEqualTo("b");
      fail("Expected CarrotException");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo(
          "???\n1: a + + b\n        ^\nExpected token of type IDENTIFIER, NUMBER_LITERAL or " +
              "STRING_LITERAL, got PLUS");
    }
  }

  @Test
  public void testEndOfStream() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("a =");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("a");
    assertThat(tokenizer.expect(TokenType.ASSIGNMENT).getType()).isEqualTo(TokenType.ASSIGNMENT);

    tokenizer = createTokenizer("b <");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("b");
    assertThat(tokenizer.expect(TokenType.LESS_THAN).getType()).isEqualTo(TokenType.LESS_THAN);

    tokenizer = createTokenizer("c >");
    assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("c");
    assertThat(tokenizer.expect(TokenType.GREATER_THAN).getType()).isEqualTo(TokenType.GREATER_THAN);

    try {
      tokenizer = createTokenizer("d \"foo");
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("d");
      tokenizer.expect(TokenType.STRING_LITERAL);
      fail("Expected CarrotException");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: d \"foo\n         ^\nUnexpected end-of-file waiting for \"");
    }
  }

  @Test
  public void testUnexpectedCharacter() {
    try {
      Tokenizer tokenizer = createTokenizer("foo \u263a bar");
      assertThat(tokenizer.expect(TokenType.IDENTIFIER).getValue()).isEqualTo("foo");
      tokenizer.expect(TokenType.IDENTIFIER);
      fail("Expected CarrotException");
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: foo \u263a bar\n        ^\nUnexpected character: \u263a");
    }
  }

  @Test
  public void testTokenEqualsToString() {
    Token t1 = new Token(TokenType.ASSIGNMENT);
    Token t2 = new Token(TokenType.EQUALITY);
    Token t3 = new Token(TokenType.IDENTIFIER, "foo");
    Token t4 = new Token(TokenType.IDENTIFIER, "bar");
    Token t5 = new Token(TokenType.IDENTIFIER, new String("bar") /* don't intern */);

    assertThat(t1).isNotEqualTo(t2);
    assertThat(t2).isNotEqualTo(t1);
    assertThat(t3).isNotEqualTo(new Object());
    assertThat(t4).isEqualTo(t5);
    assertThat(t3).isNotEqualTo(t4);
    assertThat(t1).isEqualTo(new Token(TokenType.ASSIGNMENT));

    assertThat(t1.toString()).isEqualTo("ASSIGNMENT");
    assertThat(t2.toString()).isEqualTo("EQUALITY");
    assertThat(t3.toString()).isEqualTo("IDENTIFIER <foo>");
    assertThat(t4.toString()).isEqualTo("IDENTIFIER <bar>");
    assertThat(t5.toString()).isEqualTo("IDENTIFIER <bar>");

    assertThat(t1.hashCode()).isNotEqualTo(t2.hashCode());
    assertThat(t4.hashCode()).isEqualTo(t5.hashCode());
  }

  private static Tokenizer createTokenizer(String str) throws CarrotException {
    return new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(str)));
  }
}
