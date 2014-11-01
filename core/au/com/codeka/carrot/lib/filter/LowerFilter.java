package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class LowerFilter implements Filter {

  @Override
  public Object filter(Object object, JangodInterpreter interpreter, String... arg)
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
