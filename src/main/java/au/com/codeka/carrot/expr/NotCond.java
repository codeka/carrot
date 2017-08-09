package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.ValueHelper;

import java.util.ArrayList;

/**
 * An "notcond" node. See {@link StatementParser} for the full EBNF.
 */
public class NotCond {
  private final ArrayList<AndCond> andConds;

  private NotCond(ArrayList<AndCond> andConds) {
    this.andConds = andConds;
  }

  @Override
  public String toString() {
    String str = andConds.get(0).toString();
    for (int i = 1; i < andConds.size(); i++) {
      str += " " + TokenType.LOGICAL_AND + " ";
      str += andConds.get(i).toString();
    }
    return str;
  }

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    Object value = true;
    for (AndCond andCond : andConds) {
      value = andCond.evaluate(config, scope);
      if (!ValueHelper.isTrue(value)) {
        // no need to continue if a value was evaluated to false
        break;
      }
    }
    // always return the last evaluated value
    return value;
  }

  public static class Builder {
    private ArrayList<AndCond> andConds = new ArrayList<>();

    public Builder(AndCond andCond) {
      andConds.add(andCond);
    }

    public Builder addAndCond(AndCond andCond) {
      this.andConds.add(andCond);
      return this;
    }

    public NotCond build() {
      return new NotCond(andConds);
    }
  }
}
