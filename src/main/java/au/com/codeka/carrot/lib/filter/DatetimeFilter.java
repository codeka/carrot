package au.com.codeka.carrot.lib.filter;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.CarrotInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class DatetimeFilter implements Filter {

  @Override
  public Object filter(Object object, CarrotInterpreter interpreter, String... arg)
      throws CarrotException {
    if (object == null) {
      return object;
    }
    SimpleDateFormat sdf;
    if (arg.length == 1) {
      sdf = new SimpleDateFormat(interpreter.resolveString(arg[0]));
      sdf.setTimeZone(interpreter.getConfiguration().getTimezone());
    } else if (arg.length == 2) {
      sdf = new SimpleDateFormat(interpreter.resolveString(arg[0]));
      sdf.setTimeZone(TimeZone.getTimeZone(interpreter.resolveString(arg[1])));
    } else {
      throw new InterpretException("filter date expects 1 or 2 args >>> " + arg.length);
    }
    try {
      return sdf.format(object);
    } catch (Exception e) {
      throw new InterpretException("Filter date can't format a datetime: " + object, e);
    }
  }

  @Override
  public String getName() {
    return "date";
  }

}
