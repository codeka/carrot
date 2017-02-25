package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.lib.ValueHelper;

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

  public Object evaluate() throws CarrotException {
    return ValueHelper.toNumber(getValue());
  }

  /** Returns a string representation of this {@link NumberLiteral}, useful for debugging. */
  @Override
  public String toString() {
    return getValue().toString();
  }
}
