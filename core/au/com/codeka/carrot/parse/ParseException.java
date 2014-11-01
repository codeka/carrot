package au.com.codeka.carrot.parse;

public class ParseException extends Exception {

  private static final long serialVersionUID = -2348219592540572520L;

  public ParseException(String msg) {
    super(msg);
  }

  public ParseException(String msg, Throwable e) {
    super(msg, e);
  }
}
