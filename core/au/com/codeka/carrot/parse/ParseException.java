package au.com.codeka.carrot.parse;

import au.com.codeka.carrot.base.CarrotException;

public class ParseException extends CarrotException {
  private static final long serialVersionUID = 1;

  public ParseException(String msg) {
    super(msg);
  }

  public ParseException(Throwable e) {
    super(e);
  }

  public ParseException(String msg, Throwable e) {
    super(msg, e);
  }
}
