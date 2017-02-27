package au.com.codeka.carrot.tmpl.parse;

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
  public void testEmpty() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testSingleFixed() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("Hello World!");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "Hello World!"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testSingleTag() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("{% Hello World! %}");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.TAG, " Hello World! "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testSingleEcho() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("{{ Hello World! }}");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.ECHO, " Hello World! "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testSingleComment() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("{# Hello World! #}");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.COMMENT, " Hello World! "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testFixedTagFixed() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("Hello {% foo %} world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "Hello "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.TAG, " foo "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, " world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testTagEchoFixed() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("{% foo %}{{ bar }} world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.TAG, " foo "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.ECHO, " bar "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, " world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testTagEchoComment() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("{% foo %}{{ bar }}{# baz #}");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.TAG, " foo "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.ECHO, " bar "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.COMMENT, " baz "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testTagInvalidEnd() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("stuff {% foo }} baz");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "stuff "));
    try {
      Token token = tokenizer.getNextToken();
      fail("Expected ParseException, got: " + token);
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: stuff {% foo }} baz\n                  ^\nExpected '%}'");
    }

    tokenizer = createTokenizer("blah {% foo #} baz");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "blah "));
    try {
      Token token = tokenizer.getNextToken();
      fail("Expected ParseException, got: " + token);
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: blah {% foo #} baz\n                 ^\nExpected '%}'");
    }
  }

  @Test
  public void testEchoInvalidEnd() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("stuff {{ foo %} baz");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "stuff "));
    try {
      Token token = tokenizer.getNextToken();
      fail("Expected ParseException, got: " + token);
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: stuff {{ foo %} baz\n                  ^\nExpected '}}'");
    }

    tokenizer = createTokenizer("blah {{ foo #} baz");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "blah "));
    try {
      Token token = tokenizer.getNextToken();
      fail("Expected ParseException, got: " + token);
    } catch (CarrotException e) {
      assertThat(e.getMessage()).isEqualTo("???\n1: blah {{ foo #} baz\n                 ^\nExpected '}}'");
    }
  }

  @Test
  public void testTagInnerOpenBrace() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello {% yada { yada %} world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.TAG, " yada { yada "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, " world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testTagInnerCloseBrace() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello {% yada } yada %} world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.TAG, " yada } yada "));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, " world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testFixedInnerOpenBrace() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello { world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello { world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testFixedInnerCloseBrace() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello } world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello } world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testFixedInnerCloseTag() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello %} world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello %} world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testFixedInnerCloseEcho() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello }} world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello }} world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testEscapedOpenTag() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello {\\{ world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello {{ world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  @Test
  public void testDoubleEscapedOpenTag() throws CarrotException {
    Tokenizer tokenizer = createTokenizer("hello {\\\\{ world");
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.FIXED, "hello {\\{ world"));
    assertThat(tokenizer.getNextToken()).isEqualTo(Token.create(TokenType.UNKNOWN, ""));
  }

  private static Tokenizer createTokenizer(String content) {
    return new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(content)), new TestTokenFactory());
  }

  /** Our {@link TestTokenFactory} creates instances of {@link Token}, even for {@link TokenType#UNKNOWN}. */
  private static class TestTokenFactory implements TokenFactory {
    @Override
    public Token create(TokenType type, StringBuilder content) {
      return Token.create(type, content.toString());
    }
  }
}
