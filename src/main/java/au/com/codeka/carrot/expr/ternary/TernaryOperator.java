package au.com.codeka.carrot.expr.ternary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Lazy;

/**
 * A generic ternary operator.
 */
public interface TernaryOperator {
  /**
   * Applies the binary operator to the given operands.
   *
   * <p>Note that the right operand is passed as a {@link Lazy} because some operands may not need to evaluate it,
   * depending on the left operand (e.g. boolean `and` and `or` operators).
   *
   * @param left  The left operand.
   * @param first The {@link Lazy} first operand on the right.
   * @param second The {@link Lazy} second operand on the right.
   * @return The result of the operation.
   * @throws CarrotException if there's any error applying the operator.
   */
  Object apply(Object left, Lazy first, Lazy second) throws CarrotException;
}
