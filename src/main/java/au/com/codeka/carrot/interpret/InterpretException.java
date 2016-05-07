package au.com.codeka.carrot.interpret;

import au.com.codeka.carrot.base.CarrotException;

/** Exception thrown from various interpreter subsystems. */
public class InterpretException extends CarrotException {
  private static final long serialVersionUID = 1;

  public InterpretException(String msg) {
    super(msg);
  }

  public InterpretException(Throwable e) {
    super(e);
  }

  public InterpretException(String msg, Throwable e) {
    super(msg, e);
  }
}
