package au.com.codeka.carrot.util;


public class ObjectValue {

  public static String printable(Object variable) {
    if (variable == null) {
      return "";
    }
    // TODO if String , Integer, Float, boolean....
    if (variable instanceof Long) {
      return "" + ((Long) variable).longValue();
    }
    return variable.toString();
  }
}
