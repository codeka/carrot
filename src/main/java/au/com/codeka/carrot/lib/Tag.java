package au.com.codeka.carrot.lib;

import java.io.IOException;
import java.io.Writer;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.tree.NodeList;

public interface Tag extends Importable {

  public void interpreter(NodeList carries, String helpers,
      CarrotInterpreter interpreter, Writer writer) throws CarrotException, IOException;

  /**
   * Get name of end tag lowerCase Null if it's a single tag without content.
   * 
   * @return
   */
  public String getEndTagName();

}
