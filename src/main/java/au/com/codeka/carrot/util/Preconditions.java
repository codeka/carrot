package au.com.codeka.carrot.util;

/**
 * Similar to Guava's {@link Preconditions} class, just a bunch of helper methods for asserting our preconditions.
 */
public class Preconditions {
  public static void checkNotNull(Object o) {
    if (o == null) {
      throw new IllegalStateException("Unexpected null value.");
    }
  }

  public static void checkState(boolean state) {
    if (!state) {
      throw new IllegalStateException("Unexpected state.");
    }
  }
}
