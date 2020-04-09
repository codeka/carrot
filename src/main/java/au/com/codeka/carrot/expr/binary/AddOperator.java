package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

import java.util.Objects;

/**
 * The binary ADDITION operator like in {@code a + b}.
 *
 * @author Marten Gajda
 */
public final class AddOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    if (left instanceof String || right.value() instanceof String) {
      return Objects.toString(left) + right.value();
    }
    return ValueHelper.add(left, right.value());
  }

  @Override
  public String toString() {
    return TokenType.PLUS.toString();
  }
}
