package au.com.codeka.carrot.expr.unary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.TokenType;

/**
 * The unary "+" operator like in {@code +1}.
 *
 * @author Marten Gajda
 */
public final class PlusOperator implements UnaryOperator {
  @Override
  public Object apply(Object value) throws CarrotException {
    return ValueHelper.toNumber(value);
  }


  @Override
  public String toString() {
    return TokenType.PLUS.toString();
  }
}
