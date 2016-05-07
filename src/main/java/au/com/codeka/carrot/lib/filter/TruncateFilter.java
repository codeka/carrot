package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class TruncateFilter implements Filter {

  final String ENDS = "...";

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException {
    if (object instanceof String) {
      int length = 100;
      String ends = ENDS;
      if (arg.length > 0) {
        length = Integer.valueOf(interpreter.resolveString(arg[0]));
      }
      if (arg.length > 1) {
        ends = interpreter.resolveString(arg[1]);
      }
      String string = (String) object;
      if (string.length() > length) {
        return string.substring(0, length) + ends;
      } else {
        return string;
      }
    }
    return object;
  }

  @Override
  public String getName() {
    return "truncate";
  }

}
