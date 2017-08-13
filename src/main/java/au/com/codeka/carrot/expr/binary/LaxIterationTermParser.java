package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.*;

/**
 * A factory for iterable {@link Term}s. If the term does not contain a {@code ,} this will return the sole term as is
 * and not as an {@link java.util.Iterator} Use {@link StrictIterationTermParser} if you always need an iterator, even for single terms.
 *
 * @author Marten Gajda
 */
public final class LaxIterationTermParser implements TermParser {
  private final TermParser termParser;
  private final TermParser iterationTermParser;

  public LaxIterationTermParser(TermParser termParser) {
    this.termParser = termParser;
    this.iterationTermParser = new StrictIterationTermParser(termParser);
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    Term left = termParser.parse(tokenizer);
    if (tokenizer.accept(TokenType.COMMA)) {
      // consume the comma
      tokenizer.expect(TokenType.COMMA);
      // We're in an iteration, parse all remaining elements with the strict parser
      Term right = iterationTermParser.parse(tokenizer);
      return right instanceof EmptyTerm ? new IterationTerm(left) : new BinaryTerm(left, TokenType.COMMA.binaryOperator(), right);
    }
    return left;
  }
}
