package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;

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

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    Object value = notCond.evaluate(config, scope);
    if (not) {
      return !ValueHelper.isTrue(value);
    }
    return value;
  }
}
