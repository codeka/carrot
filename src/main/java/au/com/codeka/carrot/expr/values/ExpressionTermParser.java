package au.com.codeka.carrot.expr.values;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.*;

/**
 * A {@link TermParser} which parses a constant string {@link Term} or delegates to another parser if there is no string.
 *
 * @author Marten Gajda
 */
public final class ExpressionTermParser implements TermParser {

  private final TermParser delegate;
  private final TermParser expressionParser;

  public ExpressionTermParser(TermParser delegate, TermParser expressionParser) {
    this.delegate = delegate;
    this.expressionParser = expressionParser;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    if (!tokenizer.accept(TokenType.LPAREN)) {
      // not a number, delegate to the next parser
      return delegate.parse(tokenizer);
    }
    // consume the "(".
    tokenizer.expect(TokenType.LPAREN);
    // parse the expression in between
    Term term = expressionParser.parse(tokenizer);
    // consume the ")".
    tokenizer.expect(TokenType.RPAREN);
    return term;
  }
}
