package au.com.codeka.carrot.expr.values;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.Term;

/**
 * A {@link Term} decorator which evaluates the value of the decorated term to a bound variable of the current scope.
 *
 * @author Marten Gajda
 */
public final class Variable implements Term {
  private final Term term;

  public Variable(Term term) {
    this.term = term;
  }

  @Override
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    String value = term.evaluate(config, scope).toString();
    if (value.equalsIgnoreCase("null")) {
      return null;
    }
    return scope.resolve(value);
  }


  @Override
  public String toString() {
    return term.toString();
  }
}


