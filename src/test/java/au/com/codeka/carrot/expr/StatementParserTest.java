package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.resource.ResourcePointer;
import au.com.codeka.carrot.util.LineReader;
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
  public void testComparatorThreeTerms() throws CarrotException {
    StatementParser parser = createStatementParser("one + 2 - \"three\"");
    Comparator comp = parser.parseComparator();
    assertThat(comp.toString()).isEqualTo("one PLUS 2 MINUS \"three\"");
  }

  @Test
  public void testComparatorTermsAndFactors() throws CarrotException {
    StatementParser parser = createStatementParser("one + 2 * 3 - \"three\"");
    Comparator comp = parser.parseComparator();
    assertThat(comp.toString()).isEqualTo("one PLUS 2 MULTIPLY 3 MINUS \"three\"");
  }

  @Test
  public void testVariable() throws CarrotException {
    StatementParser parser = createStatementParser("a.b['c'].d");
    Variable var = parser.parseVariable();
    assertThat(var.toString()).isEqualTo("a DOT b LSQUARE \"c\" RSQUARE  DOT d");

    parser = createStatementParser("a.b[c.d].e");
    var = parser.parseVariable();
    assertThat(var.toString()).isEqualTo("a DOT b LSQUARE c DOT d RSQUARE  DOT e");

    parser = createStatementParser("a.b[c.d['e']].f");
    var = parser.parseVariable();
    assertThat(var.toString()).isEqualTo("a DOT b LSQUARE c DOT d LSQUARE \"e\" RSQUARE  RSQUARE  DOT f");

    parser = createStatementParser("a.b(1, 2)");
    var = parser.parseVariable();
    assertThat(var.toString()).isEqualTo("a DOT b LPAREN 1 COMMA 2 RPAREN");
  }

  private StatementParser createStatementParser(String str) throws CarrotException {
    return new StatementParser(new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(str))));
  }
}
