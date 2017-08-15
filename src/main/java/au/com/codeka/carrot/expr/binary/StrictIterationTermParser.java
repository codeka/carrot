package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.*;

/**
 * A factory for iterable {@link Term}s.
 * <p>
 * Note, this *always* returns a term which evaluates to an {@link Iterable}, even if no comma is present. This is
 * different from using a {@link LaxIterationTermParser} which will
 * return a single scalar if the term is not an iteration of values.
 *
 * @author Marten Gajda
 */
public final class StrictIterationTermParser implements TermParser {
  private final TermParser termParser;

  public StrictIterationTermParser(TermParser termParser) {
    this.termParser = termParser;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    Term left = termParser.parse(tokenizer);
    if (tokenizer.accept(TokenType.COMMA)) {
      // consume the comma
      tokenizer.expect(TokenType.COMMA);
      Term right = this.parse(tokenizer);
      return right instanceof EmptyTerm ? new IterationTerm(left) : new BinaryTerm(left, TokenType.COMMA.binaryOperator(), right);
    }
    return left instanceof EmptyTerm ? left : new IterationTerm(left);
  }
}
