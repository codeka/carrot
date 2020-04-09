package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.bindings.Composite;
import au.com.codeka.carrot.bindings.SingletonBindings;
import au.com.codeka.carrot.util.MockLazyTerm;
import com.google.common.collect.ImmutableMap;
import org.dmfs.iterables.ArrayIterable;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static com.google.common.truth.Truth.assertThat;

public class InOperatorTest {
  @Test
  public void testApply() throws Exception {
    assertThat(new InOperator().apply("1", new MockLazyTerm(Arrays.asList("0", "1", "2")))).isEqualTo(true);
    assertThat(new InOperator().apply("1", new MockLazyTerm(Arrays.asList("0", "3", "2")))).isEqualTo(false);
    assertThat(new InOperator().apply("1", new MockLazyTerm(new HashSet<>(Arrays.asList("0", "1", "2"))))).isEqualTo(true);
    assertThat(new InOperator().apply("1", new MockLazyTerm(new HashSet<>(Arrays.asList("0", "3", "2"))))).isEqualTo(false);
    assertThat(new InOperator().apply("1", new MockLazyTerm(ImmutableMap.of("0", "a", "1", "b", "2", "c")))).isEqualTo(true);
    assertThat(new InOperator().apply("1", new MockLazyTerm(ImmutableMap.of("0", "a", "3", "b", "2", "c")))).isEqualTo(false);
    assertThat(new InOperator().apply("1", new MockLazyTerm(ImmutableMap.of("0", "a", "3", "1", "2", "c")))).isEqualTo(false);
    assertThat(new InOperator().apply("1", new MockLazyTerm(new Composite(new SingletonBindings("0", "a"),new SingletonBindings("1", "b"),new SingletonBindings("2", "c"))))).isEqualTo(true);
    assertThat(new InOperator().apply("1", new MockLazyTerm(new Composite(new SingletonBindings("0", "a"),new SingletonBindings("3", "b"),new SingletonBindings("2", "c"))))).isEqualTo(false);
    assertThat(new InOperator().apply("1", new MockLazyTerm(new Composite(new SingletonBindings("0", "a"),new SingletonBindings("3", "1"),new SingletonBindings("2", "c"))))).isEqualTo(false);
    assertThat(new InOperator().apply("1", new MockLazyTerm(new ArrayIterable<>("0", "1", "2")))).isEqualTo(true);
    assertThat(new InOperator().apply("1", new MockLazyTerm(new ArrayIterable<>("0", "3", "2")))).isEqualTo(false);
  }

  @Test
  public void testToString() {
    assertThat(new InOperator().toString()).isEqualTo("IN");
  }
}