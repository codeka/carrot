package au.com.codeka.carrot.lib;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.tree.NodeList;

public interface Tag extends Importable {

  public void interpreter(NodeList carries, String helpers,
      JangodInterpreter interpreter, Writer writer) throws InterpretException, IOException;

  /**
   * Get name of end tag lowerCase Null if it's a single tag without content.
   * 
   * @return
   */
  public String getEndTagName();

}
