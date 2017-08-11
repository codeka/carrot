package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.Bindings;
import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;
import org.dmfs.iterables.decorators.Filtered;
import org.dmfs.iterators.filters.AnyOf;

import java.util.Collection;
import java.util.Map;

/**
 * The binary IN operator like in {@code a in list}.
 *
 * @author Marten Gajda
 */
public final class InOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    Object rightValue = right.value();
    if (rightValue instanceof Collection) {
      return ((Collection) rightValue).contains(left);
    }
    if (rightValue instanceof Map) {
      return ((Map) rightValue).containsKey(left);
    }
    if (rightValue instanceof Bindings) {
      return ((Bindings) rightValue).resolve(left.toString()) != null;
    }
    if (rightValue instanceof Iterable) {
      return new Filtered<>((Iterable<Object>) rightValue, new AnyOf<>(left)).iterator().hasNext();
    }
    // TODO: should we also test for members?

    // TODO: would it be better to throw if you don't use it with a correct type?
    return false;
  }


  @Override
  public String toString() {
    return TokenType.IN.toString();
  }
}
