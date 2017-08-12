package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.binary.BinaryTermParser;
import au.com.codeka.carrot.expr.binary.LaxIterationTermParser;
import au.com.codeka.carrot.expr.binary.StrictIterationTermParser;
import au.com.codeka.carrot.expr.unary.UnaryTermParser;
import au.com.codeka.carrot.tag.Tag;
import au.com.codeka.carrot.tmpl.TagNode;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

/**
 * StatementParser is used to parse expressions. Expressions are used to refer to everything that appears after the
 * {@link Tag} in a {@link TagNode}, and has the following pseudo-EBNF grammar:
 * <p>
 * <pre><code>
 *
 *  value =
 *     variable
 *     | number
 *     | literal
 *     | "(" expression ")"
 *     | empty-term
 *
 *   unary-term = ["!"] unary-term
 *
 *   multiplicative-term = unnary-term [("*" | "/") multiplicative-term]
 *
 *   additive-term = multiplicative-term [("+" | "-") additive-term]
 *
 *   relational-term = additive-term [("&lt;" | &lt;=" | "&gt;" | &gt;=") relational-term]
 *
 *   equality-term = relational-term [("==" | "!=") equality-term]
 *
 *   and-term = equality-term ["&amp;&amp;" and-term]
 *
 *   or-term = and-term ["||" or-term]
 *
 *   emtpy-term =
 *
 *   expression = or-term ["," expression]
 *
 *   variable = identifier [func-call] ["[" expression "]"] ["." variable]
 *
 *   func-call = "." identifier "(" expression ")"
 *
 *   identifier = "any valid Java identifier"
 *   number = "and valid Java number"
 *   literal = """ anything """
 * </code></pre>
 * <p>
 * <p>The statement parser allows you to extract any sub-element from a string as well (for example, the ForTag
 * wants to pull off it's arguments an identifier followed by the identifier "in" followed by a statement.
 */
public class StatementParser {
  private final Tokenizer tokenizer;
  private final TermParser expressionParser;
  private final TermParser iterableParser;

  public StatementParser(Tokenizer tokenizer) {
    this.tokenizer = tokenizer;


    /*
     * Build a TermParser tree. Each TermParser receives a parser for the "sub-term" and a list of acceptable TokenTypes.
     *
     * This reflects the upper part of the grammar above.
     *
     * Operation precedence is given by the nesting level of a parser, deeper parsers have precedence over shallow factories.
     */
    TermParser base = new BinaryTermParser(
        new BinaryTermParser(
            new BinaryTermParser(
                new BinaryTermParser(
                    new BinaryTermParser(
                        new BinaryTermParser(
                            new UnaryTermParser(
                                new ValueParser(this),
                                TokenType.NOT),
                            TokenType.MULTIPLY, TokenType.DIVIDE),
                        TokenType.PLUS, TokenType.MINUS),
                    TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUAL, TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUAL),
                TokenType.EQUALITY, TokenType.INEQUALITY),
            TokenType.LOGICAL_AND),
        TokenType.LOGICAL_OR);

    // the generic expression uses a lax iteration parser
    expressionParser = new LaxIterationTermParser(base);

    // a special parser which enforces all results to be an iterable, even if it's not an iteration
    iterableParser = new StrictIterationTermParser(base);
  }

  /**
   * Parses the "end" of the statement. Just verifies that there's no unexpected tokens after the end.
   *
   * @throws CarrotException if we're not actually at the end of the statement.
   */
  public void parseEnd() throws CarrotException {
    tokenizer.end();
  }

  /**
   * Tries to parse an identifier from the stream and returns it if we parsed it, otherwise returns null.
   *
   * @return The {@link Identifier} we parsed, or null if we couldn't parse an identifier.
   * @throws CarrotException if there's some error parsing the identifer.
   */
  @Nullable
  public Identifier maybeParseIdentifier() throws CarrotException {
    if (tokenizer.accept(TokenType.IDENTIFIER)) {
      return parseIdentifier();
    }
    return null;
  }

  public Identifier parseIdentifier() throws CarrotException {
    return new Identifier(tokenizer.expect(TokenType.IDENTIFIER));
  }

  public List<Identifier> parseIdentifierList() throws CarrotException {
    List<Identifier> result = new LinkedList<>();
    // first token of a list is always an identifier
    result.add(new Identifier(tokenizer.expect(TokenType.IDENTIFIER)));
    while (tokenizer.accept(TokenType.COMMA)) {
      tokenizer.expect(TokenType.COMMA);
      result.add(new Identifier(tokenizer.expect(TokenType.IDENTIFIER)));
    }
    return result;
  }

  public NumberLiteral parseNumber() throws CarrotException {
    return new NumberLiteral(tokenizer.expect(TokenType.NUMBER_LITERAL));
  }

  public StringLiteral parseString() throws CarrotException {
    return new StringLiteral(tokenizer.expect(TokenType.STRING_LITERAL));
  }

  public boolean isAssignment() throws CarrotException {
    if (!tokenizer.accept(TokenType.ASSIGNMENT)) {
      return false;
    }
    // consume the assignment operator
    tokenizer.expect(TokenType.ASSIGNMENT);
    return true;
  }

  // TODO: consider to return the plain Term instead. Technically Expression and Term are equivalent.
  public Expression parseExpression() throws CarrotException {
    return new Expression(expressionParser.parse(tokenizer));
  }

  // TODO: at present we keep this only to test the result, check if the current tests are sufficient
  public Term parseTermsIterable() throws CarrotException {
    return iterableParser.parse(tokenizer);
  }

  Variable parseVariable() throws CarrotException {
    Identifier ident = parseIdentifier();
    Expression accessExpression = null;
    Variable dotVariable = null;
    Function args = null;
    if (tokenizer.accept(0, TokenType.DOT)
        && tokenizer.accept(1, TokenType.IDENTIFIER)
        && tokenizer.accept(2, TokenType.LPAREN)) {
      tokenizer.expect(TokenType.DOT);
      Identifier funcNameIdentifier = parseIdentifier();
      tokenizer.expect(TokenType.LPAREN);
      Term params = new EmptyTerm();
      if (!tokenizer.accept(TokenType.RPAREN)) {
        params = iterableParser.parse(tokenizer);
      }
      tokenizer.expect(TokenType.RPAREN);
      args = new Function(funcNameIdentifier, params);
    }
    if (tokenizer.accept(TokenType.LSQUARE)) {
      tokenizer.expect(TokenType.LSQUARE);
      accessExpression = parseExpression();
      tokenizer.expect(TokenType.RSQUARE);
    }
    if (tokenizer.accept(TokenType.DOT)) {
      tokenizer.expect(TokenType.DOT);
      dotVariable = parseVariable();
    }

    return new Variable(ident, args, accessExpression, dotVariable);
  }
}
