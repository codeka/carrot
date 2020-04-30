package au.com.codeka.carrot.expr.accessible;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.LazyTerm;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TokenType;

/**
 * A method call {@link Term}. It has a method term, which is the method itself and a arguments
 * term, which is an Iterable of parameters.
 */
public final class MethodTerm implements Term {
  private final AccessibleTerm method;
  private final Term arguments;

  public MethodTerm(AccessibleTerm method, Term arguments) {
    this.method = method;
    this.arguments = arguments;
  }

  @Override
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    return method.callable(config, scope).call(
        (Iterable<Object>) new LazyTerm(config, scope, arguments).value());
  }

  @Override
  public String toString() {
    return String.format(
        "%s %s %s %s",
        method.toString(),
        TokenType.LPAREN,
        arguments.toString(),
        TokenType.RPAREN);
  }
}
