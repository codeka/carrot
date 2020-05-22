package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TermParser;
import au.com.codeka.carrot.expr.TokenType;
import au.com.codeka.carrot.expr.Tokenizer;

/**
 * A factory for binary {@link Term}s.
 */
public final class BinaryTermParser implements TermParser {
  private final TermParser termParser;
  private final TokenType[] tokenTypes;

  public BinaryTermParser(TermParser termParser, TokenType... tokenTypes) {
    this.termParser = termParser;
    this.tokenTypes = tokenTypes;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    Term left = termParser.parse(tokenizer);
    while (tokenizer.accept(tokenTypes)) {
      left = new BinaryTerm(left, tokenizer.expect(tokenTypes).getType().binaryOperator(), termParser.parse(tokenizer));
    }
    return left;
  }
}
