package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.lib.Scope;
import au.com.codeka.carrot.lib.ValueHelper;

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
    if (operator != null && rhs != null) {
      str += " " + operator + " ";
      str += rhs.toString();
    }
    return str;
  }

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    Object value = lhs.evaluate(config, scope);
    if (operator != null && rhs != null) {
      if (operator.getType() == TokenType.EQUALITY) {
        Object lhsValue = value;
        Object rhsValue = rhs.evaluate(config, scope);
        return lhsValue.equals(rhsValue);
      } else {
        throw new CarrotException("TODO");
      }
    }
    return value;
  }
}
