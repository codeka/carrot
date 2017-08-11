package au.com.codeka.carrot.util;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Lazy;

/**
 * @author Marten Gajda
 */
public final class MockLazyTerm implements Lazy {
  private final Object result;

  public MockLazyTerm(Object result) {
    this.result = result;
  }

  @Override
  public Object value() throws CarrotException {
    return result;
  }
}
