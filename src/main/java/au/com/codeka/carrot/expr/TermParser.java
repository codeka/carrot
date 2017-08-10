package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;

/**
 * A generic parser for {@link Term}s.
 *
 * @author Marten Gajda
 */
public interface TermParser {

  /**
   * Return the next {@link Term} from the given {@link Tokenizer}.
   *
   * @param tokenizer A {@link Tokenizer}.
   * @return A {@link Term}
   * @throws CarrotException in the syntax of the term was invalid.
   */
  Term parse(Tokenizer tokenizer) throws CarrotException;
}
