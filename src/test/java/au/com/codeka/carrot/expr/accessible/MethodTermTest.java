package au.com.codeka.carrot.expr.accessible;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.bindings.EmptyBindings;
import au.com.codeka.carrot.expr.Term;
import org.junit.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

import static au.com.codeka.carrot.util.RenderHelper.render;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

public class MethodTermTest {
  @Test
  public void testEvaluate() throws Exception {
    final Iterable<Object> testParams = new ArrayList<>();
    final Configuration testConfiguration = new Configuration.Builder().build();
    final Scope testScope = new Scope(new EmptyBindings());
    final Object testResult = new Object();

    assertThat(
        new MethodTerm(
            new AccessibleTerm() {
              @Nonnull
              @Override
              public Callable callable(@Nonnull Configuration config, @Nonnull Scope scope) {
                assertThat(config).isSameAs(testConfiguration);
                assertThat(scope).isSameAs(testScope);
                return new Callable() {
                  @Nullable
                  @Override
                  public Object call(@Nonnull Iterable<Object> params) {
                    assertThat(params).isSameAs(testParams);
                    return testResult;
                  }
                };
              }

              @Override
              public Object evaluate(Configuration config, Scope scope) {
                fail("Evaluate called");
                return null;
              }
            },
            new Term() {
              @Override
              public Object evaluate(Configuration config, Scope scope) {
                assertThat(config).isSameAs(testConfiguration);
                assertThat(scope).isSameAs(testScope);
                return testParams;
              }
            }).evaluate(testConfiguration, testScope)).isSameAs(testResult);
  }

  @Test
  public void testNullParameters() throws CarrotException {
    final Object nullTester = new Object() {
      @SuppressWarnings("unused") // Used by the engine.
      public boolean isValueNull(String val) {
        return (val == null);
      }
    };

    assertThat(render("{% if obj.isValueNull(null) %}yep{% end %}",
        "obj", nullTester)
    ).isEqualTo("yep");
    assertThat(render("{% if obj.isValueNull(foo) %}yep{% end %}",
        "obj", nullTester,
        "foo", null)
    ).isEqualTo("yep");
    assertThat(render("{% if obj.isValueNull(foo) %}yep{% end %}",
        "obj", nullTester,
        "foo", "foo")
    ).isEmpty();
  }

  /**
   * Tests that if there's more than one overload of a method, we will chose the one that
   * @throws CarrotException
   */
  @Test
  public void testSelectBestOverload() throws CarrotException {

  }

  @Test
  public void testToString() {
    assertThat(new MethodTerm(
        new AccessibleTerm() {
          @Nonnull
          @Override
          public Callable callable(@Nonnull Configuration config, @Nonnull Scope scope) {
            fail("callable called");
            return null;
          }

          @Override
          public Object evaluate(Configuration config, Scope scope) {
            fail("Evaluate called");
            return null;
          }

          @Override
          public String toString() {
            return "methodname";
          }
        },
        new Term() {
          @Override
          public Object evaluate(Configuration config, Scope scope) {
            fail("Evaluate called");
            return null;
          }

          @Override
          public String toString() {
            return "params";
          }
        }).toString()).isEqualTo("methodname LPAREN params RPAREN");
  }

}