package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The binary DIVISION operator like in {@code a / b}.
 *
 * @author Marten Gajda
 */
public final class DivOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return ValueHelper.divide(left, right.value());
  }


  @Override
  public String toString() {
    return TokenType.DIVIDE.toString();
  }
}
