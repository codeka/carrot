package au.com.codeka.carrot.expr;

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
