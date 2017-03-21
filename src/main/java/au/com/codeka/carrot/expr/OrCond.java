package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;

import javax.annotation.Nullable;
import java.util.Objects;

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
    Object lhsValue = lhs.evaluate(config, scope);
    if (operator != null && rhs != null) {
      Object rhsValue = rhs.evaluate(config, scope);
      if (operator.getType() == TokenType.EQUALITY) {
        return ValueHelper.isEqual(lhsValue, rhsValue);
      } else if (operator.getType() == TokenType.INEQUALITY) {
        return !ValueHelper.isEqual(lhsValue, rhsValue);
      } else if (operator.getType() == TokenType.LESS_THAN) {
        return ValueHelper.compare(lhsValue, rhsValue) < 0;
      } else if (operator.getType() == TokenType.LESS_THAN_OR_EQUAL) {
        return ValueHelper.compare(lhsValue, rhsValue) <= 0;
      } else if (operator.getType() == TokenType.GREATER_THAN) {
        return ValueHelper.compare(lhsValue, rhsValue) > 0;
      } else if (operator.getType() == TokenType.GREATER_THAN_OR_EQUAL) {
        return ValueHelper.compare(lhsValue, rhsValue) >= 0;
      } else {
        throw new CarrotException("Unknown comparison: " + operator.getType());
      }
    }
    return lhsValue;
  }
}
