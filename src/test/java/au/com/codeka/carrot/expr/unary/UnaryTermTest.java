package au.com.codeka.carrot.expr.unary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.bindings.EmptyBindings;
import au.com.codeka.carrot.expr.Term;
import au.com.codeka.carrot.expr.accessible.AccessibleTerm;
import au.com.codeka.carrot.expr.accessible.Callable;
import au.com.codeka.carrot.expr.accessible.MethodTerm;
import org.junit.Test;

import javax.annotation.Nonnull;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Marten Gajda
 */
public class UnaryTermTest {
  @Test
  public void testEvaluate() throws Exception {
    final Configuration testConfiguration = new Configuration.Builder().build();
    final Scope testScope = new Scope(new EmptyBindings());
    final Object testValue = new Object();
    final Object testResult = new Object();

    assertThat(new UnaryTerm(new UnaryOperator() {
      @Override
      public Object apply(Object value) throws CarrotException {
        assertThat(value).isSameAs(testValue);
        return testResult;
      }
    }, new Term() {
      @Override
      public Object evaluate(Configuration config, Scope scope) throws CarrotException {
        assertThat(config).isSameAs(testConfiguration);
        assertThat(scope).isSameAs(testScope);
        return testValue;
      }
    }).evaluate(testConfiguration, testScope)).isSameAs(testResult);
  }

  @Test
  public void testToString() throws Exception {
    assertThat(new UnaryTerm(
        new UnaryOperator() {
          @Override
          public Object apply(Object value) throws CarrotException {
                 fail("apply called");
            return null;
          }

          @Override
          public String toString() {
            return "operator";
          }
        },
        new Term() {
          @Override
          public Object evaluate(Configuration config, Scope scope) throws CarrotException {
            fail("Evaluate called");
            return null;
          }

          @Override
          public String toString() {
            return "value";
          }
        }).toString()).isEqualTo("operator value");
  }

}