package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The binary GREATER THAN operator like in {@code a &gt; b}.
 *
 * @author Marten Gajda
 */
public final class GreaterOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return ValueHelper.compare(left, right.value()) > 0;
  }


  @Override
  public String toString() {
    return TokenType.GREATER_THAN.toString();
  }
}
