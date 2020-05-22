package au.com.codeka.carrot.expr.ternary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.TermParser;
import au.com.codeka.carrot.expr.Token;
import au.com.codeka.carrot.expr.TokenType;
import au.com.codeka.carrot.expr.Tokenizer;

public class TernaryTermParser implements TermParser {
  private final TermParser leftTermParser;
  private final TermParser firstTermParser;
  private final TermParser secondTermParser;
  private final TokenType[] tokenTypes;

  public TernaryTermParser(
      TermParser leftTermParser,
      TermParser firstParser,
      TermParser secondParser,
      TokenType... tokenTypes) {
    this.leftTermParser = leftTermParser;
    this.firstTermParser = firstParser;
    this.secondTermParser = secondParser;
    this.tokenTypes = tokenTypes;
  }

  @Override
  public Term parse(Tokenizer tokenizer) throws CarrotException {
    Term left = leftTermParser.parse(tokenizer);
    while (tokenizer.accept(tokenTypes)) {
      Token token = tokenizer.expect(tokenTypes);
      Term firstTerm = firstTermParser.parse(tokenizer);
      tokenizer.expect(token.getType().separatorType());
      Term secondTerm = secondTermParser.parse(tokenizer);
      left = new TernaryTerm(left, token.getType().ternaryOperator(), firstTerm, secondTerm);
    }
    return left;
  }
}
