package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.lib.Tag;
import au.com.codeka.carrot.tmpl.TagNode;

/**
 * StatementParser is used to parse expressions. Expressions are used to refer to everything that appears after the
 * {@link Tag} in a {@link TagNode}, and has the following pseudo-EBNF grammar:
 * <code>
 *   statement =
 *     expression
 *     | condition
 *     | identifier "(" expression {"," expression} ")"
 *
 *   condition =
 *     expression ("=="|"!="|"<"|"<="|">="|">") expression
 *
 *   expression = ["+"|"-"] term {("+"|"-") term}
 *
 *   term = factor {("*" | "/") factor}
 *
 *   factor =
 *     variable
 *     | number
 *     | literal
 *     | "(" expression ")"
 *
 *   variable = identifier ["[" expression "]"] ["." variable]
 *
 *   identifier = "any valid Java identifier"
 *   number = "and valid Java number"
 *   literal = """ anything """
 * </code>
 * <p>The statement parser allows you to extract any sub-element from a string as well (for example, the ForTag
 * wants to pull off it's arguments an identifier followed by the identifier "in" followed by a statement.
 */
public class StatementParser {
  private final Tokenizer tokenizer;

  public StatementParser(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;
  }

  public Identifier parseIdentifier() throws CarrotException {
    return new Identifier(tokenizer.expect(TokenType.IDENTIFIER));
  }

  public NumberLiteral parseNumber() throws CarrotException {
    return new NumberLiteral(tokenizer.expect(TokenType.NUMBER_LITERAL));
  }

  public StringLiteral parseString() throws CarrotException {
    return new StringLiteral(tokenizer.expect(TokenType.STRING_LITERAL));
  }

  public Variable parseVariable() throws CarrotException {
    Identifier ident = parseIdentifier();
    Expression accessExpression = null;
    Variable dotVariable = null;
    if (tokenizer.accept(TokenType.LSQUARE)) {
      tokenizer.expect(TokenType.LSQUARE);
      accessExpression = parseExpression();
      tokenizer.expect(TokenType.RSQUARE);
    }
    if (tokenizer.accept(TokenType.DOT)) {
      tokenizer.expect(TokenType.DOT);
      dotVariable = parseVariable();
    }

    return new Variable(ident, accessExpression, dotVariable);
  }

  public Factor parseFactor() throws CarrotException {
    if (tokenizer.accept(TokenType.LPAREN)) {
      tokenizer.expect(TokenType.LPAREN);
      Expression expr = parseExpression();
      tokenizer.expect(TokenType.RPAREN);

      return new Factor(expr);
    }
    if (tokenizer.accept(TokenType.STRING_LITERAL)) {
      return new Factor(parseString());
    }
    if (tokenizer.accept(TokenType.NUMBER_LITERAL)) {
      return new Factor(parseNumber());
    }
    if (tokenizer.accept(TokenType.IDENTIFIER)) {
      return new Factor(parseVariable());
    }

    throw tokenizer.unexpected("Variable, number, string or expression expected.");
  }

  public Term parseTerm() throws CarrotException {
    Token prefix;
    if (tokenizer.accept(TokenType.MULTIPLY) || tokenizer.accept(TokenType.DIVIDE)) {
      prefix = tokenizer.expect(TokenType.MULTIPLY, TokenType.DIVIDE);
    } else {
      prefix = null;
    }

    Factor factor = parseFactor();
    Term.Builder termBuilder = new Term.Builder(prefix, factor);
    while (tokenizer.accept(TokenType.MULTIPLY) || tokenizer.accept(TokenType.DIVIDE)) {
      prefix = tokenizer.expect(TokenType.MULTIPLY, TokenType.DIVIDE);
      termBuilder.addFactor(prefix, parseFactor());
    }

    return termBuilder.build();
  }

  public Expression parseExpression() throws CarrotException {
    Token prefix;
    if (tokenizer.accept(TokenType.PLUS) || tokenizer.accept(TokenType.MINUS)) {
      prefix = tokenizer.expect(TokenType.PLUS, TokenType.MINUS);
    } else {
      prefix = null;
    }

    Term term = parseTerm();
    Expression.Builder exprBuilder = new Expression.Builder(prefix, term);
    while (tokenizer.accept(TokenType.PLUS) || tokenizer.accept(TokenType.MINUS)) {
      prefix = tokenizer.expect(TokenType.PLUS, TokenType.MINUS);
      exprBuilder.addTerm(prefix, parseTerm());
    }

    return exprBuilder.build();
  }
}
