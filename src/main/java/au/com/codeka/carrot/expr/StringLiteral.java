package au.com.codeka.carrot.expr;

/**
 * A string literal.
 */
public class StringLiteral {
  private final Token token;

  public StringLiteral(Token token) {
    if (token.getType() != TokenType.STRING_LITERAL) {
      throw new IllegalStateException("Expected STRING_LITERAL");
    }

    this.token = token;
  }

  public String getValue() {
    return (String) token.getValue();
  }

  /** Returns a string representation of this {@link StringLiteral}, useful for debugging. */
  @Override
  public String toString() {
    return "\"" + getValue() + "\"";
  }
}
