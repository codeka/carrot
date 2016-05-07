package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.base.Constants;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class EscapeFilter implements Filter {

  final String samp = "&";
  final String bamp = "&amp;";
  final String sgt = ">";
  final String bgt = "&gt;";
  final String slt = "<";
  final String blt = "&lt;";
  final String bsq = "&#39;";
  final String bdq = "&quot;";

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws InterpretException {
    if (object instanceof String) {
      String value = object.toString();
      return value.replaceAll(samp, bamp)
          .replaceAll(sgt, bgt)
          .replaceAll(slt, blt)
          .replaceAll(Constants.STR_SINGLE_QUOTE, bsq)
          .replaceAll(Constants.STR_DOUBLE_QUOTE, bdq);
    }
    return object;
  }

  @Override
  public String getName() {
    return "escape";
  }

}
