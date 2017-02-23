package au.com.codeka.carrot.expr;

import java.util.ArrayList;

/**
 * A function. See {@link StatementParser} for the full EBNF.
 */
public class Function {
  private final Identifier funcName;
  private final ArrayList<Statement> params;

  private Function(Identifier funcName, ArrayList<Statement> params) {
    this.funcName = funcName;
    this.params = params;
  }

  @Override
  public String toString() {
    String str = funcName.toString();
    str += " " + TokenType.LPAREN + " ";
    for (int i = 0; i < params.size(); i++) {
      if (i > 0) {
        str += " " + TokenType.COMMA + " ";
      }
      str += params.get(i).toString();
    }
    str += " " + TokenType.RPAREN;
    return str;
  }

  public static class Builder {
    private final Identifier funcName;
    private final ArrayList<Statement> params;

    public Builder(Identifier funcName) {
      this.funcName = funcName;
      this.params = new ArrayList<>();
    }

    public Builder addParam(Statement param) {
      this.params.add(param);
      return this;
    }

    public Function build() {
      return new Function(funcName, params);
    }
  }
}
