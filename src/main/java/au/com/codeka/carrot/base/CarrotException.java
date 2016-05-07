package au.com.codeka.carrot.base;

/** Exception thrown by the public interfaces whenever there is a problem. */
public class CarrotException extends Exception {
  private static final long serialVersionUID = 1L;

  public CarrotException(String msg) {
    super(msg);
  }

  public CarrotException(Throwable innerException) {
    super(innerException);
  }

  public CarrotException(String msg, Throwable innerException) {
    super(msg, innerException);
  }
}
