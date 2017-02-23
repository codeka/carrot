package au.com.codeka.carrot.expr;

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
