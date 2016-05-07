package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.util.ObjectTruthValue;

public class DefaultFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException {
    if (ObjectTruthValue.evaluate(object)) {
      return object;
    } else {
      if (arg.length != 1) {
        throw new InterpretException("filter default expects 1 arg >>> " + arg.length);
      }
      return interpreter.resolveObject(arg[0]);
    }
  }

  @Override
  public String getName() {
    return "default";
  }

}
