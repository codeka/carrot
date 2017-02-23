package au.com.codeka.carrot.expr;

import javax.annotation.Nullable;

/**
 * An "orcond". See {@link StatementParser} for the full EBNF.
 */
public class OrCond {
  private final Comparator lhs;
  @Nullable private final Token operator;
  @Nullable private final Comparator rhs;

  public OrCond(Comparator comparator) {
    this.lhs = comparator;
    this.operator = null;
    this.rhs = null;
  }

  public OrCond(Comparator lhs, Token operator, Comparator rhs) {
    this.lhs = lhs;
    this.operator = operator;
    this.rhs = rhs;
  }

  @Override
  public String toString() {
    String str = lhs.toString();
    if (operator != null) {
      str += " " + operator + " ";
      str += rhs.toString();
    }
    return str;
  }
}
