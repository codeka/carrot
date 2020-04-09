package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.util.MockLazyTerm;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class AddOperatorTest {
  @Test
  public void testApply() throws Exception {
    assertThat(new AddOperator().apply(1, new MockLazyTerm(1))).isEqualTo(2);
    assertThat(new AddOperator().apply(1.5, new MockLazyTerm(2))).isEqualTo(3.5);
    assertThat(new AddOperator().apply("hello ", new MockLazyTerm("world"))).isEqualTo("hello world");
  }

  @Test
  public void testToString() {
    assertThat(new AddOperator().toString()).isEqualTo("PLUS");
  }
}