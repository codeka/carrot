package au.com.codeka.carrot.expr.values;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TermParser;
import au.com.codeka.carrot.expr.TokenType;
import au.com.codeka.carrot.expr.Tokenizer;

/**
 * A {@link TermParser} which parses an expression {@link Term} in parenthesis or delegates to another parser if there is
 * no opening parenthesis.
 *
 * @author Marten Gajda
 */
public final class ExpressionTermParser implements TermParser {

  private final TermParser delegate;
  private final TermParser expressionParser;

  /**
   * Creates an {@link ExpressionTermParser}.
   *
   * @param delegate         the "fallback" {@link TermParser} in case no opening parenthesis has been found.
   * @param expressionParser the {@link TermParser} to parse the expression in parenthesis.
   */
  public ExpressionTermParser(TermParser delegate, TermParser expressionParser) {
    this.delegate = delegate;
    this.expressionParser = expressionParser;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    if (!tokenizer.accept(TokenType.LPAREN)) {
      // delegate to the next parser
      return delegate.parse(tokenizer);
    }
    // consume the "(".
    tokenizer.expect(TokenType.LPAREN);
    // parse the expression in between
    Term term = expressionParser.parse(tokenizer);
    // consume the ")".
    tokenizer.expect(TokenType.LPAREN.closingType());
    return term;
  }
}
