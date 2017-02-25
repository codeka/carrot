package au.com.codeka.carrot;

/**
 * All exceptions thrown by carrot are subclasses of this exception.
 */
public class CarrotException extends Exception {
  public CarrotException(String msg) {
    super(msg);
  }

  public CarrotException(Exception cause) {
    super(cause);
  }

  public CarrotException(String msg, Exception cause) {
    super(msg, cause);
  }

  public CarrotException(String msg, int line, int col) {
    super(String.format("%s [line: %d col: %s]", msg, line, col));
  }
}

