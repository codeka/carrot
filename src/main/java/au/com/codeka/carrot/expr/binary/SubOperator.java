package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The binary SUBTRACTION operator like in {@code a - b}.
 */
public final class SubOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return ValueHelper.add(left, ValueHelper.negate(right.value()));
  }

  @Override
  public String toString() {
    return TokenType.MINUS.toString();
  }
}
