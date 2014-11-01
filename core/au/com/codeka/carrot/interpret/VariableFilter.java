package au.com.codeka.carrot.interpret;

import java.util.List;

import au.com.codeka.carrot.base.CarrotException;
import au.com.codeka.carrot.base.Constants;
import au.com.codeka.carrot.lib.Filter;
import au.com.codeka.carrot.parse.FilterParser;
import au.com.codeka.carrot.parse.ParseException;

public class VariableFilter {

  public static Object compute(String varString, CarrotInterpreter interpreter)
      throws CarrotException {
    if ((varString.startsWith(Constants.STR_SINGLE_QUOTE) && varString
        .endsWith(Constants.STR_SINGLE_QUOTE))
        ||
        (varString.startsWith(Constants.STR_DOUBLE_QUOTE) && varString
            .endsWith(Constants.STR_DOUBLE_QUOTE))) {
      return varString.substring(1, varString.length() - 1);
    }
    FilterParser fp = new FilterParser(varString);
    try {
      fp.parse();
    } catch (ParseException e) {
      throw new InterpretException(e.getMessage());
    }
    Object var = interpreter.retraceVariable(fp.getVariable());
    List<String> filters = fp.getFilters();
    if (filters.isEmpty()) {
      return var;
    }
    List<String[]> argss = fp.getArgss();
    String[] args;
    Filter filter;
    for (int i = 0; i < filters.size(); i++) {
      filter = interpreter.getConfiguration().getFilterLibrary().fetch(filters.get(i));
      args = argss.get(i);
      if (args == null) {
        var = filter.filter(var, interpreter);
      } else {
        var = filter.filter(var, interpreter, args);
      }
    }
    return var;
  }
}
