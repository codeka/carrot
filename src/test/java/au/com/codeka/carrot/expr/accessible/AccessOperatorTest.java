package au.com.codeka.carrot.expr.accessible;

import au.com.codeka.carrot.bindings.Composite;
import au.com.codeka.carrot.bindings.SingletonBindings;
import au.com.codeka.carrot.util.MockLazyTerm;
import com.google.common.collect.ImmutableMap;
import org.dmfs.iterables.ArrayIterable;
import org.junit.Test;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Marten Gajda
 */
public class AccessOperatorTest {
  @Test
  public void testApply() throws Exception {
    assertThat(new AccessOperator().apply(new String[]{"a", "b", "c"}, new MockLazyTerm(1))).isEqualTo("b");
    assertThat(new AccessOperator().apply(Arrays.asList("a", "b", "c"), new MockLazyTerm(1))).isEqualTo("b");
    assertThat(new AccessOperator().apply(ImmutableMap.of(1, "a", 2, "b", 3, "c"), new MockLazyTerm(2))).isEqualTo("b");
    assertThat(new AccessOperator().apply(new Composite(new SingletonBindings("1", "a"), new SingletonBindings("2", "b"), new SingletonBindings("3", "c")), new MockLazyTerm("2"))).isEqualTo("b");
    assertThat(new AccessOperator().apply(new FieldTestClass(), new MockLazyTerm("b"))).isEqualTo("B");
    assertThat(new AccessOperator().apply(new MethodTestClass(), new MockLazyTerm("a"))).isEqualTo("A");
    assertThat(new AccessOperator().apply(new MethodTestClass(), new MockLazyTerm("b"))).isEqualTo("B");
    assertThat(new AccessOperator().apply(new ArrayIterable<>("a", "b", "c"), new MockLazyTerm(0))).isEqualTo("a");
    assertThat(new AccessOperator().apply(new ArrayIterable<>("a", "b", "c"), new MockLazyTerm(1L))).isEqualTo("b");
    assertThat(new AccessOperator().apply(new ArrayIterable<>("a", "b", "c"), new MockLazyTerm(2L))).isEqualTo("c");
  }


  private final class FieldTestClass {
    public final String a = "A";
    public final String b = "B";
    public final String c = "C";
  }

  private final class MethodTestClass {
    public String getA() {
      return "A";
    }

    public String b() {
      return "B";
    }
  }
}