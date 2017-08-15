package au.com.codeka.carrot.expr.values;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TermParser;
import au.com.codeka.carrot.expr.Tokenizer;

/**
 * A {@link TermParser} which doesn't really parse anything, but always throws a {@link CarrotException}.
 * <p>
 * It's meant to be used as a "fail" delegate.
 *
 * @author Marten Gajda
 */
public final class ErrorTermParser implements TermParser {

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    throw new CarrotException("Missing term.");
  }
}
