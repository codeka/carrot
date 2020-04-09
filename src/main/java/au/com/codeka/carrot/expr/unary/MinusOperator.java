package au.com.codeka.carrot.expr.unary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The unary "-" operator like in {@code -a}.
 *
 * @author Marten Gajda
 */
public final class MinusOperator implements UnaryOperator {
  @Override
  public Object apply(Object value) throws CarrotException {
    return ValueHelper.negate(value);
  }


  @Override
  public String toString() {
    return TokenType.MINUS.toString();
  }
}
