package au.com.codeka.carrot.expr.values;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.EmptyTerm;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TermParser;
import au.com.codeka.carrot.expr.Tokenizer;

/**
 * A {@link TermParser} which doesn't really parse anything, but always returns an {@link EmptyTerm}.
 *
 * @author Marten Gajda
 */
public final class EmptyTermParser implements TermParser {

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    return EmptyTerm.INSTANCE;
  }
}
