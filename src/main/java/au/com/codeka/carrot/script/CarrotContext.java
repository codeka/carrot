package au.com.codeka.carrot.script;

import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.SimpleBindings;

import au.com.codeka.carrot.base.Context;

public class CarrotContext extends Context implements ScriptContext {

  private Writer errw;
  private Writer wtr;
  private Reader rd;

  public CarrotContext(Bindings global) {
    super();
    application.setGlobalBindings(global);
    sessionBindings = new SimpleBindings();
  }

  @Override
  public int getAttributesScope(String name) {
    if (sessionBindings.containsKey(name)) {
      return ENGINE_SCOPE;
    }
    if (application.getGlobalBindings().containsKey(name)) {
      return GLOBAL_SCOPE;
    }
    return -1;
  }

  @Override
  public Bindings getBindings(int scope) {
    switch (scope) {
    case ENGINE_SCOPE:
      return (Bindings) sessionBindings;
    case GLOBAL_SCOPE:
      return (Bindings) application.getGlobalBindings();
    default:
      throw new IllegalArgumentException("Illegal scope value.");
    }
  }

  @Override
  public Writer getErrorWriter() {
    return errw;
  }

  @Override
  public Reader getReader() {
    return rd;
  }

  @Override
  public List<Integer> getScopes() {
    List<Integer> scopes = new ArrayList<Integer>();
    scopes.add(ScriptContext.ENGINE_SCOPE);
    scopes.add(ScriptContext.GLOBAL_SCOPE);
    return scopes;
  }

  @Override
  public Writer getWriter() {
    return wtr;
  }

  @Override
  public Object removeAttribute(String name, int scope) {
    switch (scope) {
    case ENGINE_SCOPE:
      if (sessionBindings == null) {
        return null;
      }
      return sessionBindings.remove(name);
    case GLOBAL_SCOPE:
      if (application.getGlobalBindings() == null) {
        return null;
      }
      return application.getGlobalBindings().remove(name);
    default:
      throw new IllegalArgumentException("Illegal scope value.");
    }
  }

  @Override
  public void setBindings(Bindings bindings, int scope) {
    if (bindings == null) {
      throw new NullPointerException("Bindings cannot be null.");
    }
    switch (scope) {
    case ENGINE_SCOPE:
      sessionBindings = bindings;
      break;
    case GLOBAL_SCOPE:
      application.setGlobalBindings(bindings);
      break;
    default:
      throw new IllegalArgumentException("Illegal scope value.");
    }
  }

  @Override
  public void setErrorWriter(Writer writer) {
    errw = writer;
  }

  @Override
  public void setReader(Reader reader) {
    rd = reader;
  }

  @Override
  public void setWriter(Writer writer) {
    wtr = writer;
  }

}
