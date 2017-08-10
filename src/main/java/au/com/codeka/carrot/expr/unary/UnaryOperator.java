package au.com.codeka.carrot.expr.unary;

import au.com.codeka.carrot.CarrotException;

/**
 * An unary operator.
 *
 * @author Marten Gajda
 */
public interface UnaryOperator {
  Object apply(Object value) throws CarrotException;
}
