package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;

import java.util.ArrayList;

/**
 * An "andcond". See {@link StatementParser} for the full EBNF.
 */
public class AndCond {
  private final ArrayList<OrCond> orConds;

  private AndCond(ArrayList<OrCond> orConds) {
    this.orConds = orConds;
  }

  @Override
  public String toString() {
    String str = orConds.get(0).toString();
    for (int i = 1; i < orConds.size(); i++) {
      str += " " + TokenType.LOGICAL_OR;
    }
    return str;
  }

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    Object value = false;
    for (OrCond orCond : orConds) {
      value = orCond.evaluate(config, scope);
      if (ValueHelper.isTrue(value)) {
        // no need to continue, the result is definitely true
        break;
      }
    }
    // always return the last value
    return value;
  }


  public static class Builder {
    private ArrayList<OrCond> orConds = new ArrayList<>();

    public Builder(OrCond orCond) {
      orConds.add(orCond);
    }

    public Builder addOrCond(OrCond orCond) {
      this.orConds.add(orCond);
      return this;
    }

    public AndCond build() {
      return new AndCond(orConds);
    }
  }
}
