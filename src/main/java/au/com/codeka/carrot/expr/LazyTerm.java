package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

/**
 * A {@link Lazy} {@link Term} which is evaluated on access.
 *
 * @author Marten Gajda
 */
public final class LazyTerm implements Lazy {
  private final Configuration config;
  private final Scope scope;
  private final Term term;

  public LazyTerm(Configuration config, Scope scope, Term term) {
    this.config = config;
    this.scope = scope;
    this.term = term;
  }

  @Override
  public Object value() throws CarrotException {
    return term.evaluate(config, scope);
  }
}
