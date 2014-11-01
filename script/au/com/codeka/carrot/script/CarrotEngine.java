package au.com.codeka.carrot.script;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.base.Context;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.parse.ParseException;
import au.com.codeka.carrot.parse.TokenParser;
import au.com.codeka.carrot.util.Log;

public class CarrotEngine implements ScriptEngine {

  private String defaultBindings = "javax.script.SimpleBindings";
  private CarrotEngineFactory factory;
  private CarrotContext context;
  private Log log;

  public CarrotEngine() {
    factory = new CarrotEngineFactory();
    context = new CarrotContext(factory.globalBindings);
    log = new Log(context.getApplication().getConfiguration());
    initGlobalData();
  }

  public CarrotEngine(CarrotEngineFactory fac) {
    factory = fac;
    context = new CarrotContext(factory.globalBindings);
    initGlobalData();
  }

  private void initGlobalData() {
    // TODO set default var like version, etc.
  }

  @Override
  public Bindings createBindings() {
    try {
      return (Bindings) Class.forName(defaultBindings).newInstance();
    } catch (Exception e) {
      log.warn("Could not create bindings, using defaults: ", e);
    }
    return new SimpleBindings();
  }

  @Override
  public Object eval(String script, ScriptContext ctx) throws ScriptException {
    TokenParser parser = new TokenParser(script);
    CarrotInterpreter interpreter = new CarrotInterpreter((Context) ctx);
    try {
      return render(interpreter, parser);
    } catch (Exception e) {
      throw new ScriptException(e.getMessage());
    }
  }

  @Override
  public Object eval(Reader reader, ScriptContext ctx) throws ScriptException {
    TokenParser parser;
    try {
      parser = new TokenParser(reader);
    } catch (ParseException e) {
      throw new ScriptException(e.getMessage());
    }
    CarrotInterpreter interpreter = new CarrotInterpreter((Context) ctx);
    try {
      return render(interpreter, parser);
    } catch (Exception e) {
      throw new ScriptException(e.getMessage());
    }
  }

  @Override
  public ScriptEngineFactory getFactory() {
    return factory;
  }

  @Override
  public Object eval(String script) throws ScriptException {
    TokenParser parser = new TokenParser(script);
    CarrotInterpreter interpreter = new CarrotInterpreter((Context) context);
    try {
      return render(interpreter, parser);
    } catch (Exception e) {
      throw new ScriptException(e);
    }
  }

  @Override
  public Object eval(Reader reader) throws ScriptException {
    TokenParser parser;
    try {
      parser = new TokenParser(reader);
    } catch (ParseException e) {
      throw new ScriptException(e.getMessage());
    }
    CarrotInterpreter interpreter = new CarrotInterpreter((Context) context);
    try {
      return render(interpreter, parser);
    } catch (Exception e) {
      throw new ScriptException(e.getMessage());
    }
  }

  @Override
  public Object eval(String script, Bindings n) throws ScriptException {
    TokenParser parser = new TokenParser(script);
    ScriptContext ctx = new CarrotContext(factory.globalBindings);
    ctx.setBindings(n, ScriptContext.ENGINE_SCOPE);
    CarrotInterpreter interpreter = new CarrotInterpreter((Context) ctx);
    try {
      return render(interpreter, parser);
    } catch (Exception e) {
      throw new ScriptException(e.getMessage());
    }
  }

  @Override
  public Object eval(Reader reader, Bindings n) throws ScriptException {
    TokenParser parser;
    try {
      parser = new TokenParser(reader);
    } catch (ParseException e) {
      throw new ScriptException(e.getMessage());
    }

    ScriptContext ctx = new CarrotContext(factory.globalBindings);
    ctx.setBindings(n, ScriptContext.ENGINE_SCOPE);
    CarrotInterpreter interpreter = new CarrotInterpreter((Context) ctx);
    try {
      return render(interpreter, parser);
    } catch (Exception e) {
      throw new ScriptException(e.getMessage());
    }
  }

  private static String render(CarrotInterpreter interpreter, TokenParser parser)
      throws CarrotException, IOException {
    StringWriter writer = new StringWriter();
    interpreter.render(parser, writer);
    return writer.toString();
  }

  @Override
  public Object get(String key) {
    return getBindings(ScriptContext.ENGINE_SCOPE).get(key);
  }

  @Override
  public Bindings getBindings(int scope) {
    return context.getBindings(scope);
  }

  @Override
  public ScriptContext getContext() {
    return context;
  }

  @Override
  public void put(String key, Object value) {
    getBindings(ScriptContext.ENGINE_SCOPE).put(key, value);
  }

  @Override
  public void setBindings(Bindings bindings, int scope) {
    context.setBindings(bindings, scope);
  }

  @Override
  public void setContext(ScriptContext scontext) {
    context = (CarrotContext) scontext;
  }

}
