package au.com.codeka.carrot.lib.filter;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.util.ObjectTruthValue;

public class NotFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws InterpretException {
    return !ObjectTruthValue.evaluate(object);
  }

  @Override
  public String getName() {
    return "not";
  }

}
