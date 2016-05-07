package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class LowerFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws InterpretException {
    if (object instanceof String) {
      String value = object.toString();
      return value.toLowerCase();
    }
    return object;
  }

  @Override
  public String getName() {
    return "lower";
  }

}
