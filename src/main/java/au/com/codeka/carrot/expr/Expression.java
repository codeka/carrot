package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

/**
 * An expression.
 */
public class Expression {
  private final Term term;

  public Expression(Term term) {
    this.term = term;
  }

  @Override
  public String toString() {
    return term.toString();
  }

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    return term.evaluate(config, scope);
  }
}
