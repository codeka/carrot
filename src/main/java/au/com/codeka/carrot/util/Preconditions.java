package au.com.codeka.carrot.util;

/**
 * Similar to Guava's {@link Preconditions} class, just a bunch of helper methods for asserting our preconditions.
 */
public class Preconditions {
  public static <T> T checkNotNull(T o) {
    if (o == null) {
      throw new IllegalStateException("Unexpected null value.");
    }
    return o;
  }

  public static void checkState(boolean state) {
    if (!state) {
      throw new IllegalStateException("Unexpected state.");
    }
  }
}
