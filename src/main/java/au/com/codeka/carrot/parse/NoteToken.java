package au.com.codeka.carrot.parse;

import static au.com.codeka.carrot.parse.ParserConstants.*;

public class NoteToken extends Token {

  private static final long serialVersionUID = 6112027107603795408L;

  public NoteToken(String image) throws ParseException {
    super(image);
  }

  @Override
  public int getType() {
    return TOKEN_NOTE;
  }

  /**
   * remove all content, we don't need it.
   */
  @Override
  protected void parse() {
    // content = "";
  }

  @Override
  public String toString() {
    return "{# ----comment---- #}";
  }

}
