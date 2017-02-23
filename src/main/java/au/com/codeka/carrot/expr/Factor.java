package au.com.codeka.carrot.expr;

import javax.annotation.Nullable;

/**
 * A {@link Factor} is either variable, identifier, string literal, number literal or an expression
 * surrounded by brackets.
 */
public class Factor {
  @Nullable private final Variable variable;
  @Nullable private final NumberLiteral number;
  @Nullable private final StringLiteral string;
  @Nullable private final Statement statement;

  public Factor(Variable variable) {
    this.variable = variable;
    this.number = null;
    this.string = null;
    this.statement = null;
  }

  public Factor(NumberLiteral number) {
    this.variable = null;
    this.number = number;
    this.string = null;
    this.statement = null;
  }

  public Factor(StringLiteral string) {
    this.variable = null;
    this.number = null;
    this.string = string;
    this.statement = null;
  }

  public Factor(Statement statement) {
    this.variable = null;
    this.number = null;
    this.string = null;
    this.statement = statement;
  }

  /** Returns a string representation of this {@link Term}, useful for debugging. */
  @Override
  public String toString() {
    if (variable != null) {
      return variable.toString();
    } else if (number != null) {
      return number.toString();
    } else if (string != null) {
      return string.toString();
    } else if (statement != null) {
      return TokenType.LPAREN + " " + statement.toString() + " " + TokenType.RPAREN;
    }

    throw new IllegalStateException("Everything is null.");
  }

}
