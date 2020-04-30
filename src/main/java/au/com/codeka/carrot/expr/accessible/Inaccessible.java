package au.com.codeka.carrot.expr.accessible;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.Term;

import javax.annotation.Nonnull;

/**
 * An {@link AccessibleTerm} which can not be accessed. This serves as an {@link AccessibleTerm}
 * adapter for arbitrary terms.
 */
public final class Inaccessible implements AccessibleTerm {

  private final Term delegate;

  public Inaccessible(Term delegate) {
    this.delegate = delegate;
  }

  @Override
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    return delegate.evaluate(config, scope);
  }

  @Nonnull
  @Override
  public Callable callable(
      @Nonnull Configuration config, @Nonnull Scope scope) throws CarrotException {
    throw new CarrotException("A Inaccessible adapter can not return a callable.");
  }
}
