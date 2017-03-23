package au.com.codeka.carrot.expr;

import javax.annotation.Nullable;

/**
 * An Identifier.
 */
public class Identifier {
  private final String value;

  public Identifier(Token token) {
    if (token.getType() != TokenType.IDENTIFIER) {
      throw new IllegalStateException("Expected IDENTIFIER");
    }

    this.value = (String) token.getValue();
  }

  @Nullable
  public String evaluate() {
    if (value.equalsIgnoreCase("null")) {
      // "null" is a special identifier that... evaluates to null.
      return null;
    }

    return value;
  }

  /** Returns a string representation of this {@link Identifier}, useful for debugging. */
  @Override
  public String toString() {
    return value;
  }
}
