package au.com.codeka.carrot.expr.unary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TermParser;
import au.com.codeka.carrot.expr.TokenType;
import au.com.codeka.carrot.expr.Tokenizer;

/**
 * A factory for unary {@link Term}s.
 *
 * @author Marten Gajda
 */
public final class UnaryTermParser implements TermParser {
  private final TermParser termParser;
  private final TokenType[] tokenTypes;

  public UnaryTermParser(TermParser termParser, TokenType... tokenTypes) {
    this.termParser = termParser;
    this.tokenTypes = tokenTypes;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    if (tokenizer.accept(tokenTypes)) {
      return new UnaryTerm(tokenizer.expect(tokenTypes).getType().unaryOperator(), this.parse(tokenizer));
    }
    return termParser.parse(tokenizer);
  }
}
