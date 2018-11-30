package au.com.codeka.carrot.expr.ternary;

import org.junit.Test;

import au.com.codeka.carrot.util.MockLazyTerm;

import static com.google.common.truth.Truth.assertThat;

public class ComparionOperatorTest {
  @Test
  public void testApply() throws Exception {
    assertThat(new ComparisonOperator().apply(true, new MockLazyTerm("foo"), new MockLazyTerm("bar"))).isEqualTo("foo");
    assertThat(new ComparisonOperator().apply(false, new MockLazyTerm("foo"), new MockLazyTerm("bar"))).isEqualTo("bar");
    assertThat(new ComparisonOperator().apply(null, new MockLazyTerm("foo"), new MockLazyTerm("bar"))).isEqualTo("bar");
  }

  @Test
  public void testToString() {
    assertThat(new ComparisonOperator().toString()).isEqualTo("QUESTION");
  }
}
