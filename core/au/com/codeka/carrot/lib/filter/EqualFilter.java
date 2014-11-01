package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.base.Constants;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.util.ObjectStringEqual;

public class EqualFilter implements Filter {

  @Override
  public Object filter(Object object, JangodInterpreter interpreter, String... arg)
      throws CarrotException {
    if (arg.length != 1) {
      throw new InterpretException("filter equal expects 1 arg >>> " + arg.length);
    }
    Object argObj;
    boolean isNull = false;
    if (arg[0].startsWith(Constants.STR_SINGLE_QUOTE)
        || arg[0].startsWith(Constants.STR_DOUBLE_QUOTE)) {
      argObj = arg[0].substring(1, arg[0].length() - 1);
    } else {
      argObj = interpreter.retraceVariable(arg[0]);
      if (isNull = argObj == null) {
        argObj = arg[0];
      }
    }
    if (object == null) {
      return isNull;
    } else {
      return ObjectStringEqual.evaluate(object, argObj);
    }
  }

  @Override
  public String getName() {
    return "equal";
  }

}
