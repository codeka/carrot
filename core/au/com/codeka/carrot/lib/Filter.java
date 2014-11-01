package au.com.codeka.carrot.lib;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;

public interface Filter extends Importable {
  Object filter(Object object, JangodInterpreter interpreter, String... arg)
      throws InterpretException;
}
