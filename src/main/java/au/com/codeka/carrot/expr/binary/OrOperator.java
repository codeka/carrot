package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The binary boolean OR operator like in {@code a || b}.
 *
 * @author Marten Gajda
 */
public final class OrOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    // the result of an `OR` is true if the left operand is true or equals the value of the right operand otherwise
    return ValueHelper.isTrue(left) ? left /* === true */ : right.value();
  }


  @Override
  public String toString() {
    return TokenType.LOGICAL_OR.toString();
  }
}
