package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

import javax.annotation.Nullable;

/**
 * A {@link Factor} is either variable, identifier, string literal, number literal or an expression
 * surrounded by brackets.
 */
public class Factor {
  @Nullable private final Variable variable;
  @Nullable private final NumberLiteral number;
  @Nullable private final StringLiteral string;
  @Nullable private final Expression expression;

  public Factor(Variable variable) {
    this.variable = variable;
    this.number = null;
    this.string = null;
    this.expression = null;
  }

  public Factor(NumberLiteral number) {
    this.variable = null;
    this.number = number;
    this.string = null;
    this.expression = null;
  }

  public Factor(StringLiteral string) {
    this.variable = null;
    this.number = null;
    this.string = string;
    this.expression = null;
  }

  public Factor(Expression expression) {
    this.variable = null;
    this.number = null;
    this.string = null;
    this.expression = expression;
  }

  public Object evaluate(Configuration config, Scope scope) throws CarrotException {
    if (variable != null) {
      return variable.evaluate(config, scope);
    } else if (number != null) {
      return number.evaluate();
    } else if (string != null) {
      return string.evaluate();
    } else if (expression != null) {
      return expression.evaluate(config, scope);
    } else {
      throw new CarrotException("Everything is null.");
    }
  }

  /** Returns a string representation of this term, useful for debugging. */
  @Override
  public String toString() {
    if (variable != null) {
      return variable.toString();
    } else if (number != null) {
      return number.toString();
    } else if (string != null) {
      return string.toString();
    } else if (expression != null) {
      return TokenType.LPAREN + " " + expression.toString() + " " + TokenType.RPAREN;
    }

    throw new IllegalStateException("Everything is null.");
  }

}
