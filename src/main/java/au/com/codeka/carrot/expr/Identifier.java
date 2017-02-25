package au.com.codeka.carrot.expr;

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

  public String evaluate() {
    return value;
  }

  /** Returns a string representation of this {@link Identifier}, useful for debugging. */
  @Override
  public String toString() {
    return value;
  }
}
