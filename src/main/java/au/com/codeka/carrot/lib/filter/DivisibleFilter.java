package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class DivisibleFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException {
    if (object == null) {
      return false;
    }
    if (object instanceof Number) {
      if (arg.length != 1) {
        throw new InterpretException("filter divisible expects 1 arg >>> " + arg.length);
      }
      long factor = Long.valueOf(interpreter.resolveString(arg[0]));
      long value = ((Number) object).longValue();
      if (value % factor == 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getName() {
    return "divisible";
  }

}
