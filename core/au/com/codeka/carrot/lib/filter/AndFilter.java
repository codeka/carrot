package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.util.ObjectTruthValue;

public class AndFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException {
    if (!ObjectTruthValue.evaluate(object)) {
      return false;
    } else {
      Object test;
      for (String var : arg) {
        test = interpreter.retraceVariable(var);
        if (!ObjectTruthValue.evaluate(test)) {
          return false;
        }
      }
      return true;
    }
  }

  @Override
  public String getName() {
    return "and";
  }

}
