package au.com.codeka.carrot.parse;

import static au.com.codeka.carrot.parse.ParserConstants.*;

import java.io.IOException;
import java.io.Writer;

public class FixedToken extends Token {

  private static final long serialVersionUID = -5015884072204770458L;

  static final String toReplace = "\\{\\\\(\\\\*[\\{!#%])";
  static final String replaceWith = "{$1";

  public FixedToken(String image) throws ParseException {
    super(image);
  }

  @Override
  public int getType() {
    return TOKEN_FIXED;
  }

  /**
   * set n is an integer and > 0 change "{\[n]{" and "{\[n]!" and "{\[n]#" and
   * "{\[n]%" to "{\[n-1]{" or "{\[n-1]!" or "{\[n-1]#" or "{\[n-1]%"
   */
  @Override
  protected void parse() {
    content = image.replaceAll(toReplace, replaceWith);
  }

  public boolean isBlank() {
    return content.trim().length() == 0;
  }

  public String trim() {
    return content.trim();
  }

  public void output(Writer writer) throws IOException {
    writer.write(content);
  }

  @Override
  public String toString() {
    if (isBlank()) {
      return "{~ ~}";
    }
    return "{~ " + content + " ~}";
  }
}
