package au.com.codeka.carrot.lib.filter;

import javax.script.ScriptContext;
import javax.script.SimpleBindings;

import org.junit.BeforeClass;

import au.com.codeka.carrot.base.Context;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.script.JangodContext;

public class ZzzBase {

  protected static CarrotInterpreter compiler;
  protected Filter filter;

  @BeforeClass
  public static void setUpClass() throws Exception {
    SimpleBindings bindings = new SimpleBindings();
    ScriptContext context = new JangodContext(bindings);
    compiler = new CarrotInterpreter((Context) context);
  }
}
