package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;
import org.dmfs.iterables.decorators.Flattened;
import org.dmfs.iterators.SingletonIterator;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Iterator;

/**
 * The binary ITERATION operator like in {@code a, b}.
 *
 * @author Marten Gajda
 */
public final class IterationOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    return new Flattened<>(Collections.singleton(left), new LazyIterable(right));
  }

  @Override
  public String toString() {
    return TokenType.COMMA.toString();
  }


  /**
   * An {@link Iterable} which flattens the value of the right hand side of an IterableOperator.
   */
  private final class LazyIterable implements Iterable<Object> {
    private final Lazy value;

    public LazyIterable(Lazy value) {
      this.value = value;
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    @Override
    public Iterator<Object> iterator() {
      try {
        Object val = value.value();
        return val instanceof Iterable ? ((Iterable) val).iterator() : new SingletonIterator<>(val);
      } catch (CarrotException e) {
        // TODO: find more appropriate exception
        throw new IllegalStateException("can't iterate elements", e);
      }
    }
  }
}
