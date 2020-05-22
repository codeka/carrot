package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;
import au.com.codeka.carrot.util.ValueHelper;

/**
 * The "elvis" operator, '?:'. Returns the left hand side of the expression if it's "truthy" (e.g.
 * a non-null string, a non-zero integer etc), otherwise it returns the right hand side.
 */
public class ElvisOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return ValueHelper.isTrue(left) ? left : right.value();
  }

  @Override
  public String toString() {
    return TokenType.ELVIS.toString();
  }
}
