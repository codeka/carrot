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
  EOF(false),

  /**
   * A string literal "like this".
   */
  STRING_LITERAL(true),

  /**
   * A number literal, like 12 or 12.34.
   */
  NUMBER_LITERAL(true),

  /**
   * A Java-style identifier like foo or bar.
   */
  IDENTIFIER(true),

  /**
   * Left-parenthesis: (
   */
  LPAREN(false),

  /**
   * Right-parenthesis: )
   */
  RPAREN(false),

  /**
   * Left-square-bracket: [
   */
  LSQUARE(false),

  /**
   * Right-square-bracket: ]
   */
  RSQUARE(false),

  /**
   * Single Equals: =
   */
  ASSIGNMENT(false),

  COMMA(false, new IterationOperator()),
  DOT(false),
  NOT(false, null, new NotOperator()),
  LOGICAL_AND(false, new AndOperator()),
  LOGICAL_OR(false, new OrOperator()),
  EQUALITY(false, new EqOperator()),
  INEQUALITY(false, new Complement(EQUALITY.binaryOperator)),
  LESS_THAN(false, new LessOperator()),
  GREATER_THAN(false, new GreaterOperator()),
  LESS_THAN_OR_EQUAL(false, new Complement(GREATER_THAN.binaryOperator)),
  GREATER_THAN_OR_EQUAL(false, new Complement(LESS_THAN.binaryOperator)),
  PLUS(false, new AddOperator(), new PlusOperator()),
  MINUS(false, new SubOperator(), new MinusOperator()),
  MULTIPLY(false, new MulOperator()),
  DIVIDE(false, new DivOperator());

  private final boolean hasValue;
  private final BinaryOperator binaryOperator;
  private final UnaryOperator unaryOperator;

  TokenType(boolean hasValue) {
    this(hasValue, null, null);
  }

  TokenType(boolean hasValue, BinaryOperator binaryOperator) {
    this(hasValue, binaryOperator, null);
  }

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
