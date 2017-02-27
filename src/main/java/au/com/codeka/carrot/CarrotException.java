package au.com.codeka.carrot;

import au.com.codeka.carrot.resource.ResourcePointer;

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

  public CarrotException(Exception cause, ResourcePointer ptr) {
    super(String.format("%s\n%s", ptr, cause.getMessage()), cause);
  }

  public CarrotException(String msg, Exception cause) {
    super(msg, cause);
  }

  public CarrotException(String msg, ResourcePointer ptr) {
    super(String.format("%s\n%s", ptr, msg));
  }
}

