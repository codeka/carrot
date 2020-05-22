package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.util.MockLazyTerm;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static com.google.common.truth.Truth.assertThat;

public class ElivsOperatorTest {
  @Test
  public void testApply() throws Exception {
    assertThat(new ElvisOperator().apply("hello", new MockLazyTerm("world"))).isEqualTo("hello");
    assertThat(new ElvisOperator().apply(null, new MockLazyTerm("world"))).isEqualTo("world");
    assertThat(new ElvisOperator().apply("", new MockLazyTerm("world"))).isEqualTo("world");

    assertThat(new ElvisOperator().apply(true, new MockLazyTerm(1))).isEqualTo(true);
    assertThat(new ElvisOperator().apply(false, new MockLazyTerm(1))).isEqualTo(1);

    assertThat(new ElvisOperator().apply(10, new MockLazyTerm(1))).isEqualTo(10);
    assertThat(new ElvisOperator().apply(0, new MockLazyTerm(2))).isEqualTo(2);

    ArrayList<Integer> numbers = Lists.newArrayList(10);
    assertThat(new ElvisOperator().apply(numbers, new MockLazyTerm(2))).isEqualTo(numbers);
    assertThat(new ElvisOperator().apply(new ArrayList<String>(),
        new MockLazyTerm(2))).isEqualTo(2);

    HashMap<String, Integer> map = Maps.newHashMap();
    map.put("hello", 10);
    assertThat(new ElvisOperator().apply(map, new MockLazyTerm(2))).isEqualTo(map);
    assertThat(new ElvisOperator().apply(Maps.newHashMap(),
        new MockLazyTerm(2))).isEqualTo(2);
  }

  @Test
  public void testToString() {
    assertThat(new ElvisOperator().toString()).isEqualTo("ELVIS");
  }
}
