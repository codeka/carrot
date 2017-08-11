package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

import java.util.Collections;

/**
 * An empty {@link Term}. Empty terms always evaluate to an empty {@link Iterable}.
 *
 * @author Marten Gajda
 */
public final class EmptyTerm implements Term {
  @Override
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    return Collections.emptyList();
  }

  @Override
  public String toString() {
    return "";
  }
}
