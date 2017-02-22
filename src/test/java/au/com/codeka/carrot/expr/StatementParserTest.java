package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.StringReader;

import static com.google.common.truth.Truth.assertThat;

/**
 * Tests for {@link StatementParserTest}.
 */
@RunWith(JUnit4.class)
public class StatementParserTest {
  @Test
  public void testExpressionThreeTerms() throws CarrotException {
    StatementParser parser = createStatementParser("one + 2 - \"three\"");
    Expression expr = parser.parseExpression();
    assertThat(expr.toString()).isEqualTo("one PLUS 2 MINUS \"three\"");
  }

  @Test
  public void testExpressionTermsAndFactors() throws CarrotException {
    StatementParser parser = createStatementParser("one + 2 * 3 - \"three\"");
    Expression expr = parser.parseExpression();
    assertThat(expr.toString()).isEqualTo("one PLUS 2 MULTIPLY 3 MINUS \"three\"");
  }

  @Test
  public void testVariable() throws CarrotException {
    StatementParser parser = createStatementParser("a.b['c'].d");
    Variable var = parser.parseVariable();
    assertThat(var.toString()).isEqualTo("a.b[\"c\"].d");

    parser = createStatementParser("a.b[c.d].e");
    var = parser.parseVariable();
    assertThat(var.toString()).isEqualTo("a.b[c.d].e");

    parser = createStatementParser("a.b[c.d['e']].f");
    var = parser.parseVariable();
    assertThat(var.toString()).isEqualTo("a.b[c.d[\"e\"]].f");
  }

  private StatementParser createStatementParser(String str) throws CarrotException {
    return new StatementParser(new Tokenizer(new StringReader(str)));
  }
}
