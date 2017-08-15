package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

import javax.annotation.Nullable;

/**
 * A {@link Factor} is either variable, identifier, string literal, number literal or an expression
 * surrounded by brackets.
 */
public class Factor {
  @Nullable private final Variable variable;

  public Factor(Variable variable) {
    this.variable = variable;
  }



  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    if (variable != null) {
      return variable.evaluate(config, scope);
    } else {
      throw new CarrotException("Everything is null.");
    }
  }

  /** Returns a string representation of this term, useful for debugging. */
  @Override
  public String toString() {
    if (variable != null) {
      return variable.toString();
    }

    throw new IllegalStateException("Everything is null.");
  }

}
