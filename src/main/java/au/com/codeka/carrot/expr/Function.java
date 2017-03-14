package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.Configuration;
import au.com.codeka.carrot.Scope;
import au.com.codeka.carrot.util.Log;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * A function identifier and a list of arguments to that function. See {@link StatementParser} for the full EBNF.
 */
public class Function {
  private final Identifier funcName;
  private final ArrayList<Expression> args;

  private Function(Identifier funcName, ArrayList<Expression> args) {
    this.funcName = funcName;
    this.args = args;
  }

  @Override
  public String toString() {
    String str = funcName.toString() + " " + TokenType.LPAREN + " ";
    for (int i = 0; i < args.size(); i++) {
      if (i > 0) {
        str += " " + TokenType.COMMA + " ";
      }
      str += args.get(i).toString();
    }
    str += " " + TokenType.RPAREN;
    return str;
  }

  /**
   * Evaluate this function on the given object, with the given {@link Configuration} and {@link Scope}.
   *
   * @param value The value to evaluate this function on. That is, the object on which we want to call the function
   *              this object represents.
   * @param config The current {@link Configuration}.
   * @param scope The current {@link Scope}.
   * @return The result of calling the function (i.e. the function's return value).
   * @throws CarrotException if there's any exceptions calling the function.
   */
  public Object evaluate(Object value, Configuration config, Scope scope) throws CarrotException {
    Class<?>[] paramTypes = new Class<?>[args.size()];
    Object[] paramValues = new Object[args.size()];
    for (int i = 0; i < args.size(); i++) {
      Object arg = args.get(i).evaluate(config, scope);
      paramTypes[i] = arg.getClass();
      paramValues[i] = arg;
    }

    try {
      Method method = findMethod(config, value.getClass(), funcName.evaluate(), paramTypes, paramValues);
      method.setAccessible(true);
      return method.invoke(value, paramValues);
    } catch (InvocationTargetException | IllegalAccessException e) {
      throw new CarrotException(e); // TODO: getPointer()
    }
  }

  /**
   * Attempts a basic sort of overload matching, looking for a method on the given class that we can call.
   *
   * <p>Unlike normal Java overload resolution, we just return the <em>first</em> method that matches close enough. So
   * if there's a method that takes an int and one that takes a long (say), we make no guarantee about which one will
   * be returned.</p>
   *
   * @param config The {@link Configuration}.
   * @param cls The {@link Class} to search for a method for.
   * @param name The name of the function we want to search for (not case sensitive, unlike normal Java).
   * @param paramTypes The types of the parameters we have. We'll attempt to find a method that matches the given
   *                   types as closely as possible (for example, a method that takes an int is as good as one that
   *                   takes longs).
   * @param paramValues The values we're going to execute. If the types don't match exactly, we may need to convert
   *                    some of the parameters first.
   * @return The {@link Method} that matches the given name and parameter values.
   * @throws CarrotException If no method matches.
   */
  private Method findMethod(
      Configuration config,
      Class<?> cls,
      String name,
      Class<?>[] paramTypes,
      Object[] paramValues) throws CarrotException {

    // This is a list of "candidate" methods that we tried and rejected for whatever reason. Useful in the error
    // message that we throw if there's no perfect method.
    ArrayList<String> candidates = new ArrayList<>();
    for (Method method : cls.getMethods()) {
      if (!method.getName().equalsIgnoreCase(name)) {
        continue;
      }

      if (method.getParameterCount() != paramTypes.length) {
        Log.debug(config, "Wrong number of arguments: %d: %s", paramTypes.length, method);
        candidates.add(method.toString());
        continue;
      }

      boolean allMatch = true;
      Class<?>[] methodParamTypes = method.getParameterTypes();
      for (int i = 0; i < methodParamTypes.length; i++) {
        if (!methodParamTypes[i].isAssignableFrom(paramTypes[i])) {
          Object convertedValue = convertType(methodParamTypes[i], paramValues[i]);
          if (convertedValue != null) {
            paramValues[i] = convertedValue;
          } else {
            Log.debug(config,
                "Param %d (%s) not assignable from %s: %s", i, methodParamTypes[i], paramTypes[i], method);
            candidates.add(method.toString());
            allMatch = false;
            break;
          }
        }
      }
      if (allMatch) {
        for (int i = 0; i < method.getParameterCount(); i++) {
          paramValues[i] = convertType(method.getParameterTypes()[i], paramValues[i]);
        }
        return method;
      }
    }

    throw new CarrotException(String.format("No matching method '%s' found on class %s, candidates: [%s]",
        name, cls.getName(), String.join(",", candidates)));
  }

  /**
   * Attempts to perform a conversion from the given value to the given output type.
   *
   * @param outputType The output type that we want.
   * @param value The value that we want to convert.
   * @return The converted value, or null if no conversion is possible.
   */
  @Nullable
  private static Object convertType(Class<?> outputType, Object value) {
    if (value == null) {
      throw new NullPointerException("Value cannot be null.");
    }
    if (outputType.equals(value.getClass())) {
      return value;
    }
    if (outputType.isAssignableFrom(value.getClass())) {
      return value;
    }
    if ((byte.class.equals(outputType) || Byte.class.equals(outputType)) && value instanceof Number) {
      return ((Number) value).byteValue();
    }
    if ((short.class.equals(outputType) || Short.class.equals(outputType)) && value instanceof Number) {
      return ((Number) value).shortValue();
    }
    if ((int.class.equals(outputType) || Integer.class.equals(outputType)) && value instanceof Number) {
      return ((Number) value).intValue();
    }
    if ((long.class.equals(outputType) || Long.class.equals(outputType)) && value instanceof Number) {
      return ((Number) value).longValue();
    }
    if ((float.class.equals(outputType) || Float.class.equals(outputType)) && value instanceof Number) {
      return ((Number) value).floatValue();
    }
    if ((double.class.equals(outputType) || Double.class.equals(outputType)) && value instanceof Number) {
      return ((Number) value).doubleValue();
    }

    return null;
  }

  /**
   * Simple builder class for {@link Function}.
   */
  public static class Builder {
    private final Identifier funcName;
    private final ArrayList<Expression> args;

    /**
     * Construct a new {@link Builder} for a function with the given {@link Identifier} as it's name.
     *
     * @param funcName The name of this function.
     */
    public Builder(Identifier funcName) {
      this.funcName = funcName;
      this.args = new ArrayList<>();
    }

    /**
     * Add the given {@link Expression} as an argument to the function.
     *
     * @param arg The {@link Expression} to add as an argument.
     * @return This {@link Builder}.
     */
    public Builder addParam(Expression arg) {
      this.args.add(arg);
      return this;
    }

    /**
     * @return The built {@link Function}.
     */
    public Function build() {
      return new Function(funcName, args);
    }
  }
}
