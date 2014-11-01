package au.com.codeka.carrot.lib.filter;

import static au.com.codeka.carrot.util.logging.JangodLogger;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class TruncateFilter implements Filter {

  final String ENDS = "...";

  @Override
  public Object filter(Object object, JangodInterpreter interpreter, String... arg)
      throws InterpretException {
    if (object instanceof String) {
      int length = 100;
      String ends = ENDS;
      if (arg.length > 0) {
        try {
          length = Integer.valueOf(interpreter.resolveString(arg[0]));
        } catch (Exception e) {
          JangodLogger.warning("filter truncate get length error use default >>> 100");
        }
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
