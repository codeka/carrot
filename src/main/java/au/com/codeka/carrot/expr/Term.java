package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

/**
 * A generic term.
 *
 * @author Marten Gajda
 */
public interface Term {
  Object evaluate(Configuration config, Scope scope) throws CarrotException;
}
