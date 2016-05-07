package au.com.codeka.carrot.script;

import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.SimpleBindings;

import au.com.codeka.carrot.base.Constants;

public class CarrotEngineFactory implements ScriptEngineFactory {

  protected Bindings globalBindings = new SimpleBindings();

  public void setGlobalBindings(Bindings bindings) {
    globalBindings = bindings;
  }

  @Override
  public String getEngineName() {
    return "Carrot template engine";
  }

  @Override
  public String getEngineVersion() {
    return "0.28";
  }

  @Override
  public List<String> getExtensions() {
    List<String> ext = new ArrayList<String>();
    ext.add("god");
    ext.add("tpl");
    ext.add("html");
    ext.add("carrot");
    return ext;
  }

  @Override
  public String getLanguageName() {
    return "Carrot";
  }

  @Override
  public String getLanguageVersion() {
    return "1.0";
  }

  @Override
  public String getMethodCallSyntax(String obj, String m, String... args) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<String> getMimeTypes() {
    return new ArrayList<String>();
  }

  @Override
  public List<String> getNames() {
    List<String> names = new ArrayList<String>();
    names.add("Carrot");
    names.add("Django");
    names.add("Jinja");
    return names;
  }

  @Override
  public String getOutputStatement(String toDisplay) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Object getParameter(String key) {
    if ("THREADING".equals(key))
      return "THREAD-ISOLATED";
    else if (ScriptEngine.ENGINE.equals(key))
      return getEngineName();
    else if (ScriptEngine.ENGINE_VERSION.equals(key))
      return getEngineVersion();
    else if (ScriptEngine.NAME.equals(key))
      return getEngineName();
    else if (ScriptEngine.LANGUAGE.equals(key))
      return getLanguageName();
    else if (ScriptEngine.LANGUAGE_VERSION.equals(key))
      return getLanguageVersion();
    else
      return null;
  }

  @Override
  public String getProgram(String... statements) {
    StringBuilder buff = new StringBuilder();
    for (String statement : statements) {
      buff.append(statement).append(Constants.STR_NEW_LINE);
    }
    return buff.toString();
  }

  @Override
  public ScriptEngine getScriptEngine() {
    return new CarrotEngine(this);
  }

  @Override
  public String toString() {
    return getEngineName() + " v" + getEngineVersion();
  }

}
