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
 *     | identifier "(" statement {"," statement} ")"
 *
 *   expression = ["!"] notcond
 *
 *   notcond = andcond {"&&" andcond}
 *
 *   andcond = orcond {"||" orcond}
 *
 *   orcond =
 *     comparator [("=="|"!="|"<"|"<="|">="|">") comparator]
 *
 *   comparator = ["+"|"-"] term {("+"|"-") term}
 *
 *   term = factor {("*" | "/") factor}
 *
 *   factor =
 *     variable
 *     | number
 *     | literal
 *     | "(" statement ")"
 *
 *   variable = identifier ["[" statement "]"] ["." variable]
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

  /** Parses the "end" of the statement. Just verifies that there's no unexpected tokens after the end. */
  public void parseEnd() throws CarrotException {
    tokenizer.end();
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

  public Statement parseStatement() throws CarrotException {
    if (tokenizer.accept(TokenType.IDENTIFIER) && tokenizer.accept(1, TokenType.LPAREN)) {
      Identifier identifier = parseIdentifier();
      Function.Builder funcBuilder = new Function.Builder(identifier);
      tokenizer.expect(TokenType.LPAREN);
      while (!tokenizer.accept(TokenType.RPAREN)) {
        funcBuilder.addParam(parseStatement());
        tokenizer.expect(TokenType.COMMA);
      }
      tokenizer.expect(TokenType.RPAREN);
      return new Statement(funcBuilder.build());
    } else {
      return new Statement(parseExpression());
    }
  }

  Expression parseExpression() throws CarrotException {
    boolean not = false;
    if (tokenizer.accept(TokenType.NOT)) {
      not = true;
    }

    return new Expression(not, parseNotCond());
  }

  NotCond parseNotCond() throws CarrotException {
    NotCond.Builder notCondBuilder = new NotCond.Builder(parseAndCond());
    while (tokenizer.accept(TokenType.LOGICAL_AND)) {
      tokenizer.expect(TokenType.LOGICAL_AND);
      notCondBuilder.addAndCond(parseAndCond());
    }
    return notCondBuilder.build();
  }

  AndCond parseAndCond() throws CarrotException {
    AndCond.Builder andCondBuilder = new AndCond.Builder(parseOrCond());
    while (tokenizer.accept(TokenType.LOGICAL_OR)) {
      tokenizer.expect(TokenType.LOGICAL_OR);
      andCondBuilder.addOrCond(parseOrCond());
    }
    return andCondBuilder.build();
  }

  OrCond parseOrCond() throws CarrotException {
    Comparator lhs = parseComparator();
    TokenType[] validTypes = new TokenType[] {
        TokenType.EQUALITY,
        TokenType.INEQUALITY,
        TokenType.LESS_THAN,
        TokenType.LESS_THAN_OR_EQUAL,
        TokenType.GREATER_THAN_OR_EQUAL,
        TokenType.GREATER_THAN
    };
    if (tokenizer.accept(validTypes)) {
      Token operator = tokenizer.expect(validTypes);
      Comparator rhs = parseComparator();
      return new OrCond(lhs, operator, rhs);
    } else {
      return new OrCond(lhs);
    }
  }

  Variable parseVariable() throws CarrotException {
    Identifier ident = parseIdentifier();
    Statement accessStatement = null;
    Variable dotVariable = null;
    if (tokenizer.accept(TokenType.LSQUARE)) {
      tokenizer.expect(TokenType.LSQUARE);
      accessStatement = parseStatement();
      tokenizer.expect(TokenType.RSQUARE);
    }
    if (tokenizer.accept(TokenType.DOT)) {
      tokenizer.expect(TokenType.DOT);
      dotVariable = parseVariable();
    }

    return new Variable(ident, accessStatement, dotVariable);
  }

  Factor parseFactor() throws CarrotException {
    if (tokenizer.accept(TokenType.LPAREN)) {
      tokenizer.expect(TokenType.LPAREN);
      Statement stmt = parseStatement();
      tokenizer.expect(TokenType.RPAREN);

      return new Factor(stmt);
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

  Term parseTerm() throws CarrotException {
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

  Comparator parseComparator() throws CarrotException {
    Token prefix;
    if (tokenizer.accept(TokenType.PLUS) || tokenizer.accept(TokenType.MINUS)) {
      prefix = tokenizer.expect(TokenType.PLUS, TokenType.MINUS);
    } else {
      prefix = null;
    }

    Term term = parseTerm();
    Comparator.Builder exprBuilder = new Comparator.Builder(prefix, term);
    while (tokenizer.accept(TokenType.PLUS) || tokenizer.accept(TokenType.MINUS)) {
      prefix = tokenizer.expect(TokenType.PLUS, TokenType.MINUS);
      exprBuilder.addTerm(prefix, parseTerm());
    }

    return exprBuilder.build();
  }
}
