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
  @Nullable private final Statement accessStatement;
  @Nullable private final Variable dotVariable;

  public Variable(Identifier identifier, @Nullable Statement accessStatement, @Nullable Variable dotVariable) {
    this.identifier = identifier;
    this.accessStatement = accessStatement;
    this.dotVariable = dotVariable;
  }

  /** Gets a string representation of the {@link Variable}, useful for debugging. */
  @Override
  public String toString() {
    String str = identifier.toString();
    if (accessStatement != null) {
      str += " " + TokenType.LSQUARE + " " + accessStatement + " " + TokenType.RSQUARE + " ";
    }
    if (dotVariable != null) {
      str += " " + TokenType.DOT + " " + dotVariable.toString();
    }
    return str;
  }
}
