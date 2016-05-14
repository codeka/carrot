package au.com.codeka.carrot.parse;

import static au.com.codeka.carrot.parse.ParserConstants.*;

import java.util.List;

public class EchoToken extends Token {

  private static final long serialVersionUID = 8307037212944170832L;

  private FilterParser fp;

  public EchoToken(String image) throws ParseException {
    super(image);
  }

  public String getVariable() {
    return fp.getVariable();
  }

  /**
   * get the filters
   * 
   * @return filters
   */
  public List<String> getFilters() {
    return fp.getFilters();
  }

  /**
   * @return The filter's args.
   */
  public List<String[]> getArgss() {
    return fp.getArgss();
  }

  /**
   * Get var and filters Like that if image =
   * {{obj.attr.attr|filter1:"ar|g1",arg2|filter2:'a:",b"c' }} then var =
   * obj.attr.attr filter = [ [filter1,ar|g1,arg2], [filter2,a:",b"c] ]
   * 
   * @throws ParseException If there was an error parsing the token.
   */
  @Override
  protected void parse() throws ParseException {
    fp = new FilterParser(image.substring(2, image.length() - 2).trim());
    fp.parse();
  }

  @Override
  public String toString() {
    String s = "{{ " + fp.getVariable();
    int i, j;
    for (i = 0; i < fp.getFilters().size(); i++) {
      s += "|" + fp.getFilters().get(i);
      String[] args = fp.getArgss().get(i);
      if (args != null) {
        s += ":";
        for (j = 0; j < args.length; j++) {
          s += args[j];
          if (j < args.length - 1) {
            s += ",";
          }
        }
      }
    }
    s += " }}";
    return s;
  }

  @Override
  public int getType() {
    return TOKEN_ECHO;
  }
}
