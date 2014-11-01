package au.com.codeka.carrot.lib.filter;

import static au.com.codeka.carrot.util.logging.JangodLogger;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.util.logging.Level;

public class DatetimeFilter implements Filter {

  @Override
  public Object filter(Object object, JangodInterpreter interpreter, String... arg)
      throws InterpretException {
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
      JangodLogger.log(Level.SEVERE, "filter date can't format a datetime >>> " + object,
          e.getCause());
    }
    return object;
  }

  @Override
  public String getName() {
    return "date";
  }

}
