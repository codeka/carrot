package au.com.codeka.carrot.expr.binary;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.CollectionHelper;
import au.com.codeka.carrot.expr.Lazy;
import au.com.codeka.carrot.expr.TokenType;

import java.util.ArrayList;

/**
 * The binary ITERATION operator like in {@code a, b}.
 */
public final class IterationOperator implements BinaryOperator {
  @Override
  public Object apply(Object left, Lazy right) throws CarrotException {
    // Slowly building up an ArrayList like this isn't very efficient, but there's not going to be
    // a lot of these typically.
    ArrayList<Object> flattened = new ArrayList<>();
    flattened.add(left);
    Object rightValue = right.value();
    if (rightValue instanceof Iterable) {
      flattened.addAll(CollectionHelper.toArrayList((Iterable) rightValue));
    } else {
      flattened.add(right);
    }
    return flattened;
  }

  @Override
  public String toString() {
    return TokenType.COMMA.toString();
  }
}
