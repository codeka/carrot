package au.com.codeka.carrot.expr.values;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.expr.*;

/**
 * A {@link TermParser} which parses a constant string {@link Term} or delegates to another parser if there is no string.
 *
 * @author Marten Gajda
 */
public final class StringTermParser implements TermParser {

  private final TermParser delegate;

  public StringTermParser(TermParser delegate) {
    this.delegate = delegate;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    if (!tokenizer.accept(TokenType.STRING_LITERAL)) {
      // not a number, delegate to the next parser
      return delegate.parse(tokenizer);
    }
    return new StringTerm(tokenizer.expect(TokenType.STRING_LITERAL));
  }

  /**
   * A trivial term containing only a constant string.
   */
  private static final class StringTerm implements Term {
    private final Token token;

    public StringTerm(Token token) {
      this.token = token;
    }

    @Override
    public Object evaluate(Configuration config, Scope scope) throws CarrotException {
      return token.getValue();
    }

    @Override
    public String toString() {
      return "\"" + token.getValue().toString() + "\"";
    }
  }
}
