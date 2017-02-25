package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

import javax.annotation.Nullable;

/**
 * A "statement". See {@link StatementParser} for the full EBNF.
 */
public class Statement {
  @Nullable private Expression expression;
  @Nullable private Function function;

  public Statement(Expression expression) {
    this.expression = expression;
    this.function = null;
  }

  public Statement(Function function) {
    this.expression = null;
    this.function = function;
  }

  @Override
  public String toString() {
    if (expression != null) {
      return expression.toString();
    } else if (function != null) {
      return function.toString();
    } else {
      throw new IllegalStateException("One of function or expression should be non-null.");
    }
  }

  /** Evaluate this {@link Statement} in the given {@link Scope}. */
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    Object value;
    if (expression != null) {
      value = expression.evaluate(config, scope);
    } else {
      value = function.evaluate(config, scope);
    }

    return value;
  }
}
