package au.com.codeka.carrot.expr.ternary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.LazyTerm;
import au.com.codeka.carrot.expr.Term;

/**
 * A ternary term.
 */
public class TernaryTerm implements Term {
  private final Term left;
  private final TernaryOperator operation;
  private final Term first;
  private final Term second;

  public TernaryTerm(Term left, TernaryOperator operation, Term first, Term second) {
    this.left = left;
    this.operation = operation;
    this.first = first;
    this.second = second;
  }


  @Override
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    return operation.apply(
        left.evaluate(config, scope),
        new LazyTerm(config, scope, first),
        new LazyTerm(config, scope, second));
  }


  @Override
  public String toString() {
    return String.format("%s %s (%s) (%s)",
        left.toString(),
        operation.toString(),
        first.toString(),
        second.toString());
  }
}
