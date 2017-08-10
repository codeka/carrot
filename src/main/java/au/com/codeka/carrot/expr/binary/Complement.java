package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.ValueHelper;
import au.com.codeka.carrot.expr.Lazy;

/**
 * The boolean complement operator of another {@link BinaryOperator}.
 *
 * @author Marten Gajda
 */
public final class Complement implements BinaryOperator {
  private final BinaryOperator delegate;

  public Complement(BinaryOperator delegate) {
    this.delegate = delegate;
  }

  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return !ValueHelper.isTrue(delegate.apply(left, right));
  }

  @Override
  public String toString() {
    return "<NOT>";
  }
}
