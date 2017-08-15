package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.bindings.EmptyBindings;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


/**
 * @author Marten Gajda
 */
public class EmptyTermTest {
  @Test
  public void testEvaluate() throws Exception {
    assertThat(((Iterable) new EmptyTerm().evaluate(new Configuration(), new Scope(new EmptyBindings()))).iterator().hasNext()).isFalse();
  }

  @Test
  public void testToString() throws Exception {
    assertThat(new EmptyTerm().toString()).isEqualTo("");
  }

}