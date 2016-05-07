package au.com.codeka.carrot.lib.filter;

import java.math.BigDecimal;
import java.math.BigInteger;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class AbsFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws InterpretException {
    if (object instanceof Integer) {
      return Math.abs((Integer) object);
    }
    if (object instanceof Float) {
      return Math.abs((Float) object);
    }
    if (object instanceof Long) {
      return Math.abs((Long) object);
    }
    if (object instanceof Short) {
      return (short) Math.abs((Short) object);
    }
    if (object instanceof Double) {
      return Math.abs((Double) object);
    }
    if (object instanceof BigDecimal) {
      return ((BigDecimal) object).abs();
    }
    if (object instanceof BigInteger) {
      return ((BigInteger) object).abs();
    }
    if (object instanceof Byte) {
      return (byte) Math.abs((Byte) object);
    }
    if (object instanceof String) {
      try {
        return new BigDecimal(object.toString()).abs();
      } catch (Exception e) {
        throw new InterpretException(object + " can't be dealed with abs filter");
      }
    }
    return object;
  }

  @Override
  public String getName() {
    return "abs";
  }

}
