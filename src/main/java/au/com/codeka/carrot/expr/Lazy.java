package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;

/**
 * A value which is evaluated lazily on access.
 *
 * @author Marten Gajda
 */
public interface Lazy {

  Object value() throws CarrotException;
}
