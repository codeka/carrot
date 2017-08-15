package au.com.codeka.carrot.expr.unary;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


/**
 * @author Marten Gajda
 */
public class NotOperatorTest {
  @Test
  public void testApply() throws Exception {
    assertThat(new NotOperator().apply(true)).isEqualTo(false);
    assertThat(new NotOperator().apply(false)).isEqualTo(true);
    assertThat(new NotOperator().apply(10.65)).isEqualTo(false);
    assertThat(new NotOperator().apply(0)).isEqualTo(true);
    assertThat(new NotOperator().apply("xyz")).isEqualTo(false);
    assertThat(new NotOperator().apply("")).isEqualTo(true);
  }

  @Test
  public void testToString() throws Exception {
    assertThat(new NotOperator().toString()).isEqualTo("NOT");
  }

}