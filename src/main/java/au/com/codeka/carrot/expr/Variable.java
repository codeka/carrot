package au.com.codeka.carrot.expr;

import javax.annotation.Nullable;

/**
 * A {@link Variable} is has the following EBNF form:
 *
 * <code>variable = identifier ["." variable | "[" expression "]"]
 *
 * See {@link StatementParser} for the full grammar.
 */
public class Variable {
  private final Identifier identifier;
  @Nullable private final Expression accessExpression;
  @Nullable private final Variable dotVariable;

  public Variable(Identifier identifier, @Nullable Expression accessExpression, @Nullable Variable dotVariable) {
    this.identifier = identifier;
    this.accessExpression = accessExpression;
    this.dotVariable = dotVariable;
  }

  /** Gets a string representation of the {@link Variable}, useful for debugging. */
  @Override
  public String toString() {
    String str = identifier.toString();
    if (accessExpression != null) {
      str += "[" + accessExpression + "]";
    }
    if (dotVariable != null) {
      str += "." + dotVariable.toString();
    }
    return str;
  }
}
