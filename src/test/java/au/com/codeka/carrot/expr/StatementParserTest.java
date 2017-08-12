package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.bindings.EmptyBindings;
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


  @Test
  public void testBinaryOperation() throws CarrotException {
    assertThat(createStatementParser("1+1").parseTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).isEqualTo(2);
    assertThat(createStatementParser("1+1+1").parseTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).isEqualTo(3);
    assertThat(createStatementParser("1+1+1+1").parseTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).isEqualTo(4);
    assertThat(createStatementParser("1").parseTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).isEqualTo(1);
    assertThat(createStatementParser("!1").parseTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).isEqualTo(false);
    assertThat(createStatementParser("!!1").parseTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).isEqualTo(true);
    assertThat(createStatementParser("!!!1").parseTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).isEqualTo(false);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testIterableTerms() throws CarrotException {
    assertThat((Iterable<Object>) createStatementParser("1, 2").parseTermsIterable().evaluate(new Configuration(), new Scope(new EmptyBindings()))).containsAllOf(1L, 2L);
    assertThat((Iterable<Object>) createStatementParser("1, 2, 3").parseTermsIterable().evaluate(new Configuration(), new Scope(new EmptyBindings()))).containsAllOf(1L, 2L, 3L);
    assertThat((Iterable<Object>) createStatementParser("1, 2 + 5, \"3\", 4").parseTermsIterable().evaluate(new Configuration(), new Scope(new EmptyBindings()))).containsAllOf(1L, 7L, "3", 4L);
  }


  private StatementParser createStatementParser(String str) throws CarrotException {
    return new StatementParser(new Tokenizer(new LineReader(new ResourcePointer(null), new StringReader(str))));
  }
}
