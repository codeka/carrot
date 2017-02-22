package au.com.codeka.carrot.expr;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * A {@link Token} is something pulled off the statement stream and represents a terminal like a string literal,
 * number, identifier, etc.
 */
public class Token {
  private final TokenType tokenType;
  @Nullable private final Object value;

  public Token(TokenType tokenType) {
    this.tokenType = tokenType;
    this.value = null;
  }

  public Token(TokenType tokenType, Object value) {
    this.tokenType = tokenType;
    this.value = value;
  }

  public TokenType getType() {
    return tokenType;
  }

  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    String str = tokenType.toString();
    if (tokenType.hasValue()) {
      str += " <" + value + ">";
    }
    return str;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Token)) {
      return false;
    }

    Token otherToken = (Token) other;
    if (otherToken.tokenType != tokenType) {
      return false;
    }

    if (tokenType.hasValue()) {
      return Objects.equals(otherToken.value, value);
    }

    return true;
  }

  @Override
  public int hashCode() {
    if (tokenType.hasValue()) {
      return Objects.hash(tokenType, value);
    }
    return tokenType.hashCode();
  }
}
