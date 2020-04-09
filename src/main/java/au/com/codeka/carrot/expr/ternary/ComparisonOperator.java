package au.com.codeka.carrot.expr.ternary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

public class ComparisonOperator implements TernaryOperator {
  @Override
  public Object apply(Object left, Lazy first, Lazy second) throws CarrotException {
    if (ValueHelper.isTrue(left)) {
      return first.value();
    } else {
      return second.value();
    }
  }

  @Override
  public String toString() {
    return TokenType.QUESTION.toString();
  }
}
