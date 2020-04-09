package au.com.codeka.carrot.expr.values;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.util.ValueHelper;
import au.com.codeka.carrot.expr.*;

/**
 * A {@link TermParser} which parses a constant number {@link Term} or delegates to another parser if there is no number.
 *
 * @author Marten Gajda
 */
public final class NumberTermParser implements TermParser {

  private final TermParser delegate;

  public NumberTermParser(TermParser delegate) {
    this.delegate = delegate;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    if (!tokenizer.accept(TokenType.NUMBER_LITERAL)) {
      // not a number, delegate to the next parser
      return delegate.parse(tokenizer);
    }
    return new NumberTerm(tokenizer.expect(TokenType.NUMBER_LITERAL));
  }

  /**
   * A trivial term containing only a constant number.
   */
  private static final class NumberTerm implements Term {
    private final Token token;

    public NumberTerm(Token token) {
      this.token = token;
    }

    @Override
    public Object evaluate(Configuration config, Scope scope) throws CarrotException {
      return ValueHelper.toNumber(token.getValue());
    }

    @Override
    public String toString() {
      return token.getValue().toString();
    }
  }
}
