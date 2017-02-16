package au.com.codeka.carrot.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import au.com.codeka.carrot.base.CarrotException;

public class Variable {
  private static Pattern SPLIT_REGEX = Pattern.compile("[\\[\\]\\.]");

  private String name;
  private List<String> chainList;

  public Variable(String variable) {
    split(variable);
  }

  private void split(String variable) {
    String[] parts = SPLIT_REGEX.split(variable);
    if (parts.length == 1) {
      name = variable;
      chainList = null;
    } else {
      name = parts[0];
      chainList = new ArrayList<>();
      for (int i = 1; i < parts.length; i++) {
        String part = parts[i].trim();
        if (!part.isEmpty()) {
          chainList.add(part);
        }
      }
    }
  }

  public String getName() {
    return name;
  }

  public Object resolve(Object value) throws CarrotException {
    if (chainList != null) {
      return new VariableChain(chainList, value).resolve();
    } else {
      return value;
    }
  }

  @Override
  public String toString() {
    return "<Variable: " + name + ">";
  }
}
