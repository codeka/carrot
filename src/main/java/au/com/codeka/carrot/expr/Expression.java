package au.com.codeka.carrot.expr;

/**
 * An expression.
 */
public class Expression {
  private final boolean not;
  private final NotCond notCond;

  public Expression(boolean not, NotCond notCond) {
    this.not = not;
    this.notCond = notCond;
  }

  @Override
  public String toString() {
    if (not) {
      return "!" + notCond.toString();
    } else {
      return notCond.toString();
    }
  }
}
