package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The binary boolean AND operator like in {@code a &amp;&amp; b}.
 */
public final class AndOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    // the result of an `AND` depends on the right operand if the left operand is true, otherwise
    // it's false
    return ValueHelper.isTrue(left) ? right.value() : left /* === false*/;
  }


  @Override
  public String toString() {
    return TokenType.LOGICAL_AND.toString();
  }
}
