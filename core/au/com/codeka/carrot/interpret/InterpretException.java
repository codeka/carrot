package au.com.codeka.carrot.interpret;

public class InterpretException extends Exception {

  private static final long serialVersionUID = -3471306977643116138L;

  public InterpretException(String msg) {
    super(msg);
  }

  public InterpretException(String msg, Throwable e) {
    super(msg, e);
  }
}
