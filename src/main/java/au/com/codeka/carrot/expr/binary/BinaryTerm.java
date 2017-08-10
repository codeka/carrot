package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.LazyTerm;
import au.com.codeka.carrot.expr.Term;

/**
 * A binary {@link Term}. It has a left and a right (Sub-){@link Term} as well as a {@link BinaryOperator}.
 *
 * @author Marten Gajda
 */
public final class BinaryTerm implements Term {
  private final Term left;
  private final BinaryOperator operation;
  private final Term right;

  public BinaryTerm(Term left, BinaryOperator operation, Term right) {
    this.left = left;
    this.operation = operation;
    this.right = right;
  }


  @Override
  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    return operation.apply(left.evaluate(config, scope), new LazyTerm(config, scope, right));
  }


  @Override
  public String toString() {
    return String.format("%s %s %s", left.toString(), operation.toString(), right.toString());
  }
}
