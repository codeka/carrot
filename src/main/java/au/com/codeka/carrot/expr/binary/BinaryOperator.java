package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Lazy;

/**
 * A generic binary operator.
 */
public interface BinaryOperator {
  /**
   * Applies the binary operator to the given operands.
   *
   * <p>Note that the right operand is passed as a {@link Lazy} because some operands may not need to evaluate it,
   * depending on the left operand (e.g. boolean `and` and `or` operators).
   *
   * @param left  The left operand.
   * @param right The {@link Lazy} right operand.
   * @return The result of the operation.
   * @throws CarrotException if there's any error applying the operator.
   */
  Object apply(Object left, Lazy right) throws CarrotException;
}
