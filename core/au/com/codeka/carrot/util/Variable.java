package au.com.codeka.carrot.util;

import java.util.Arrays;
import java.util.List;

public class Variable {

  static final String DOT = ".";
  static final String DOT_REGX = "\\.";

  private String name;
  private List<String> chainList;

  public Variable(String variable) {
    split(variable);
  }

  private void split(String variable) {
    if (!variable.contains(DOT)) {
      name = variable;
      chainList = null;
      return;
    }

    String[] parts = variable.split(DOT_REGX);
    name = parts[0];
    chainList = Arrays.asList(parts);
    chainList = chainList.subList(1, chainList.size());

  }

  public String getName() {
    return name;
  }

  public Object resolve(Object value) {
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
