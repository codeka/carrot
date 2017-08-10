package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.Term;

import java.util.Collections;

/**
 * Adapter which makes the evaluated value of a {@link Term} {@link Iterable}.
 *
 * @author Marten Gajda
 */
public class IterableTerm implements Term {
  private final Term left;

  public IterableTerm(Term left) {
    this.left = left;
  }

  @Override
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    return Collections.singleton(left.evaluate(config, scope));
  }

  @Override
  public String toString() {
    return left.toString();
  }
}
