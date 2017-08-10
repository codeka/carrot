package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The binary MULTIPLICATION operator like in {@code a * b}.
 *
 * @author Marten Gajda
 */
public final class MulOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return ValueHelper.multiply(left, right.value());
  }


  @Override
  public String toString() {
    return TokenType.MULTIPLY.toString();
  }
}
