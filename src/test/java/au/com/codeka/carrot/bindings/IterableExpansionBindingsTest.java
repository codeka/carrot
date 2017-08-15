package au.com.codeka.carrot.bindings;

import au.com.codeka.carrot.expr.Identifier;
import au.com.codeka.carrot.expr.Token;
import au.com.codeka.carrot.expr.TokenType;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Marten Gajda
 */
public class IterableExpansionBindingsTest {
  @Test
  public void testResolve() throws Exception {
    assertThat(new IterableExpansionBindings(Collections.<Identifier>emptyList(), Collections.emptyList()).resolve("xyz")).isNull();
    assertThat(new IterableExpansionBindings(Collections.singleton(new Identifier(new Token(TokenType.IDENTIFIER, "xyz"))), Collections.singleton(new Object())).resolve("abc")).isNull();
    assertThat(new IterableExpansionBindings(Collections.singleton(new Identifier(new Token(TokenType.IDENTIFIER, "xyz"))), Collections.<Object>singleton("123")).resolve("xyz")).isEqualTo("123");
    assertThat(new IterableExpansionBindings(
        Arrays.asList(new Identifier(new Token(TokenType.IDENTIFIER, "a")), new Identifier(new Token(TokenType.IDENTIFIER, "b"))),
        Arrays.<Object>asList("A", "B")).resolve("xyz"))
        .isNull();
    assertThat(new IterableExpansionBindings(
        Arrays.asList(new Identifier(new Token(TokenType.IDENTIFIER, "a")), new Identifier(new Token(TokenType.IDENTIFIER, "b"))),
        Arrays.<Object>asList("A", "B")).resolve("a"))
        .isEqualTo("A");
    assertThat(new IterableExpansionBindings(
        Arrays.asList(new Identifier(new Token(TokenType.IDENTIFIER, "a")), new Identifier(new Token(TokenType.IDENTIFIER, "b"))),
        Arrays.<Object>asList("A", "B")).resolve("b"))
        .isEqualTo("B");
    assertThat(new IterableExpansionBindings(
        Arrays.asList(new Identifier(new Token(TokenType.IDENTIFIER, "a")), new Identifier(new Token(TokenType.IDENTIFIER, "b"))),
        Arrays.<Object>asList("A", "B", "C")).resolve("a"))
        .isEqualTo("A");
    assertThat(new IterableExpansionBindings(
        Arrays.asList(new Identifier(new Token(TokenType.IDENTIFIER, "a")), new Identifier(new Token(TokenType.IDENTIFIER, "b"))),
        Arrays.<Object>asList("A", "B", "C")).resolve("b"))
        .isEqualTo("B");
    assertThat(new IterableExpansionBindings(
        Arrays.asList(new Identifier(new Token(TokenType.IDENTIFIER, "a")), new Identifier(new Token(TokenType.IDENTIFIER, "b"))),
        Arrays.<Object>asList("A", "B", "C")).resolve("c"))
        .isNull();
    assertThat(new IterableExpansionBindings(
        Arrays.asList(
            new Identifier(new Token(TokenType.IDENTIFIER, "a")),
            new Identifier(new Token(TokenType.IDENTIFIER, "b")),
            new Identifier(new Token(TokenType.IDENTIFIER, "c"))),
        Arrays.<Object>asList("A", "B")).resolve("a"))
        .isEqualTo("A");
    assertThat(new IterableExpansionBindings(
        Arrays.asList(
            new Identifier(new Token(TokenType.IDENTIFIER, "a")),
            new Identifier(new Token(TokenType.IDENTIFIER, "b")),
            new Identifier(new Token(TokenType.IDENTIFIER, "c"))),
        Arrays.<Object>asList("A", "B")).resolve("b"))
        .isEqualTo("B");
    assertThat(new IterableExpansionBindings(
        Arrays.asList(
            new Identifier(new Token(TokenType.IDENTIFIER, "a")),
            new Identifier(new Token(TokenType.IDENTIFIER, "b")),
            new Identifier(new Token(TokenType.IDENTIFIER, "c"))),
        Arrays.<Object>asList("A", "B")).resolve("c"))
        .isNull();
  }

  @Test
  public void testIsEmpty() throws Exception {
    assertTrue(new IterableExpansionBindings(Collections.<Identifier>emptyList(), Collections.emptyList()).isEmpty());
    assertFalse(new IterableExpansionBindings(Collections.singleton(new Identifier(new Token(TokenType.IDENTIFIER, "test"))), Collections.singleton(new Object())).isEmpty());
  }

}