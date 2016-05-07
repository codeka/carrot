package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;

public interface Filter extends Importable {
  Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException;
}
