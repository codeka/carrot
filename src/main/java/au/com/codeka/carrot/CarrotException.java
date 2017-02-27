package au.com.codeka.carrot;

import au.com.codeka.carrot.resource.ResourcePointer;

/**
 * All exceptions thrown by carrot are subclasses of this exception.
 */
public class CarrotException extends Exception {
  private final ResourcePointer ptr;

  public CarrotException(String msg) {
    super(msg);
    ptr = null;
  }

  public CarrotException(Exception cause) {
    super(cause);
    ptr = null;
  }

  public CarrotException(Exception cause, ResourcePointer ptr) {
    super(String.format("%s\n%s", ptr, cause.getMessage()), cause);
    this.ptr = ptr;
  }

  public CarrotException(String msg, Exception cause) {
    super(msg, cause);
    ptr = null;
  }

  public CarrotException(String msg, ResourcePointer ptr) {
    super(String.format("%s\n%s", ptr, msg));
    this.ptr = ptr;
  }

  public ResourcePointer getPointer() {
    return ptr;
  }
}

