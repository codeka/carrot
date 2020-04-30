package au.com.codeka.carrot.expr.accessible;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.*;
import au.com.codeka.carrot.expr.values.EmptyTermParser;
import au.com.codeka.carrot.expr.values.IdentifierTermParser;
import au.com.codeka.carrot.expr.values.Variable;

/**
 * A parser for access {@link Term}s.
 *
 * @author Marten Gajda
 */
public final class AccessTermParser implements TermParser {
  private final TermParser valueParser;
  private final TermParser expressionTerm;
  private final TermParser identifierTerm;
  private final TermParser iterationTerm;

  public AccessTermParser(TermParser expressionTerm, TermParser identifierTerm, TermParser iterationTerm) {
    this.valueParser = new IdentifierTermParser(new EmptyTermParser());
    this.iterationTerm = iterationTerm;
    this.expressionTerm = expressionTerm;
    this.identifierTerm = identifierTerm;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    Term left = valueParser.parse(tokenizer);
    if (left instanceof EmptyTerm) {
      return left;
    }
    AccessibleTerm result = new Inaccessible(new Variable(left));

    while (tokenizer.accept(TokenType.DOT, TokenType.LSQUARE, TokenType.LPAREN)) {
      Token token = tokenizer.expect(TokenType.DOT, TokenType.LSQUARE, TokenType.LPAREN);

      if (token.getType() == TokenType.DOT) {
        result = new AccessTerm(result, new AccessOperator(), identifierTerm.parse(tokenizer), TokenType.DOT);
      }
      if (token.getType() == TokenType.LSQUARE) {
        // the accessor in [] is supposed to be any expression
        result = new AccessTerm(result, new AccessOperator(), expressionTerm.parse(tokenizer), TokenType.LSQUARE);
      }
      if (token.getType() == TokenType.LPAREN) {
        // the accessor in () is supposed to be an iteration
        result = new Inaccessible(new MethodTerm(result, iterationTerm.parse(tokenizer)));
      }

      if (token.getType().closingType() != null) {
        tokenizer.expect(token.getType().closingType());
      }
    }
    return result;
  }
}
