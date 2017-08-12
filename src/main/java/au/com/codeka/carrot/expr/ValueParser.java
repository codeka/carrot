package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;

/**
 * A {@link TermParser} which parses value terms like identifiers, function calls, literals and numbers.
 * <p>
 * TODO: refactor this a bit further and get rid of {@link Factor}.
 *
 * @author Marten Gajda
 */
public final class ValueParser implements TermParser {

  private final StatementParser statementParser;

  public ValueParser(StatementParser statementParser) {
    this.statementParser = statementParser;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {

    if (tokenizer.accept(TokenType.LPAREN)) {
      tokenizer.expect(TokenType.LPAREN);
      Expression expr = statementParser.parseExpression();
      tokenizer.expect(TokenType.RPAREN);

      return new TermAdapter(new Factor(expr));
    }
    if (tokenizer.accept(TokenType.STRING_LITERAL)) {
      return new TermAdapter(new Factor(statementParser.parseString()));
    }
    if (tokenizer.accept(TokenType.NUMBER_LITERAL)) {
      return new TermAdapter(new Factor(statementParser.parseNumber()));
    }
    if (tokenizer.accept(TokenType.IDENTIFIER)) {
      return new TermAdapter(new Factor(statementParser.parseVariable()));
    }

    return new EmptyTerm();
  }

  private static class TermAdapter implements Term {
    private final Factor factor;

    public TermAdapter(Factor factor) {
      this.factor = factor;
    }

    @Override
    public Object evaluate(Configuration config, Scope scope) throws CarrotException {
      return factor.evaluate(config, scope);
    }

    @Override
    public String toString() {
      return factor.toString();
    }
  }
}
