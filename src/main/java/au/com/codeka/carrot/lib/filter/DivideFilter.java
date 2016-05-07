package au.com.codeka.carrot.lib.filter;

import java.math.BigDecimal;
import java.math.BigInteger;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class DivideFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException {
    if (arg.length != 1) {
      throw new InterpretException("filter multiply expects 1 arg >>> " + arg.length);
    }
    Object toMul = interpreter.resolveObject(arg[0]);
    Number num;
    if (toMul instanceof String) {
      num = new BigDecimal(toMul.toString());
    } else if (toMul instanceof Number) {
      num = (Number) toMul;
    } else {
      return object;
    }
    if (object instanceof Integer) {
      return (Integer) object / num.intValue();
    }
    if (object instanceof Float) {
      return (Float) object / num.floatValue();
    }
    if (object instanceof Long) {
      return (Long) object / num.longValue();
    }
    if (object instanceof Short) {
      return (Short) object / num.shortValue();
    }
    if (object instanceof Double) {
      return (Double) object / num.doubleValue();
    }
    if (object instanceof BigDecimal) {
      return ((BigDecimal) object).divide(BigDecimal.valueOf(num.doubleValue()));
    }
    if (object instanceof BigInteger) {
      return ((BigInteger) object).divide(BigInteger.valueOf(num.longValue()));
    }
    if (object instanceof Byte) {
      return (Byte) object / num.byteValue();
    }
    if (object instanceof String) {
      try {
        return Double.valueOf(object.toString()) / num.doubleValue();
      } catch (Exception e) {
        throw new InterpretException(object + " can't be dealed with multiply filter");
      }
    }
    return object;
  }

  @Override
  public String getName() {
    return "divide";
  }

}
