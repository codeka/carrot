package au.com.codeka.carrot.lib.filter;

import java.math.BigDecimal;
import java.math.BigInteger;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class MultiplyFilter implements Filter {

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
      return 0L + num.intValue() * (Integer) object;
    }
    if (object instanceof Float) {
      return 0D + num.floatValue() * (Float) object;
    }
    if (object instanceof Long) {
      return num.longValue() * (Long) object;
    }
    if (object instanceof Short) {
      return 0 + num.shortValue() * (Short) object;
    }
    if (object instanceof Double) {
      return num.doubleValue() * (Double) object;
    }
    if (object instanceof BigDecimal) {
      return ((BigDecimal) object).multiply(BigDecimal.valueOf(num.doubleValue()));
    }
    if (object instanceof BigInteger) {
      return ((BigInteger) object).multiply(BigInteger.valueOf(num.longValue()));
    }
    if (object instanceof Byte) {
      return num.byteValue() * (Byte) object;
    }
    if (object instanceof String) {
      try {
        return num.doubleValue() * Double.valueOf(object.toString());
      } catch (Exception e) {
        throw new InterpretException(object + " can't be dealed with multiply filter");
      }
    }
    return object;
  }

  @Override
  public String getName() {
    return "multiply";
  }

}
