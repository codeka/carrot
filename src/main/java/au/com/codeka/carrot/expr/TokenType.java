package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.expr.binary.*;
import au.com.codeka.carrot.expr.unary.MinusOperator;
import au.com.codeka.carrot.expr.unary.NotOperator;
import au.com.codeka.carrot.expr.unary.PlusOperator;
import au.com.codeka.carrot.expr.unary.UnaryOperator;

/**
 * An enumeration of the different types of {@link Token}s we can pull off the statement parser.
 */
public enum TokenType {
  /**
   * An unknown token, or the end of the stream.
   */
  EOF(false, null, null),

  /**
   * A string literal "like this".
   */
  STRING_LITERAL(true, null, null),

  /**
   * A number literal, like 12 or 12.34.
   */
  NUMBER_LITERAL(true, null, null),

  /**
   * A Java-style identifier like foo or bar.
   */
  IDENTIFIER(true, null, null),

  /**
   * Left-parenthesis: (
   */
  LPAREN(false, null, null),

  /**
   * Right-parenthesis: (
   */
  RPAREN(false, null, null),

  /**
   * Left-square-bracket: [
   */
  LSQUARE(false, null, null),

  /**
   * Right-square-bracket: ]
   */
  RSQUARE(false, null, null),

  /**
   * Single Equals: =
   */
  ASSIGNMENT(false, null, null),

  COMMA(false, null, null),
  DOT(false, null, null),
  NOT(false, null, new NotOperator()),
  LOGICAL_AND(false, new AndOperator(), null),
  LOGICAL_OR(false, new OrOperator(), null),
  EQUALITY(false, new EqOperator(), null),
  INEQUALITY(false, new Complement(EQUALITY.binaryOperator), null),
  LESS_THAN(false, new LessOperator(), null),
  GREATER_THAN(false, new GreaterOperator(), null),
  LESS_THAN_OR_EQUAL(false, new Complement(GREATER_THAN.binaryOperator), null),
  GREATER_THAN_OR_EQUAL(false, new Complement(LESS_THAN.binaryOperator), null),
  PLUS(false, new AddOperator(), new PlusOperator()),
  MINUS(false, new SubOperator(), new MinusOperator()),
  MULTIPLY(false, new MulOperator(), null),
  DIVIDE(false, new DivOperator(), null);

  private final boolean hasValue;
  private final BinaryOperator binaryOperator;
  private final UnaryOperator unaryOperator;

  TokenType(boolean hasValue, BinaryOperator binaryOperator, UnaryOperator unaryOperator) {
    this.hasValue = hasValue;
    this.binaryOperator = binaryOperator;
    this.unaryOperator = unaryOperator;
  }

  public boolean hasValue() {
    return hasValue;
  }

  public BinaryOperator binaryOperator() {
    if (binaryOperator == null) {
      throw new UnsupportedOperationException(String.format("%s is not a binary operator", this.toString()));
    }
    return binaryOperator;
  }

  public UnaryOperator unaryOperator() {
    if (unaryOperator == null) {
      throw new UnsupportedOperationException(String.format("%s is not an unary operator", this.toString()));
    }
    return unaryOperator;
  }
}
