package au.com.codeka.carrot.expr;

/**
 * A number (integer or double).
 */
public class NumberLiteral {
  private final Token token;

  public NumberLiteral(Token token) {
    if (token.getType() != TokenType.NUMBER_LITERAL) {
      throw new IllegalStateException("Expected NUMBER_LITERAL");
    }

    this.token = token;
  }

  public Object getValue() {
    return token.getValue();
  }

  /** Returns a string representation of this {@link NumberLiteral}, useful for debugging. */
  @Override
  public String toString() {
    return getValue().toString();
  }
}
