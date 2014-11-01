package au.com.codeka.carrot.parse;

import static au.com.codeka.carrot.parse.ParserConstants.*;

import java.io.Serializable;

public abstract class Token implements Serializable {

  private static final long serialVersionUID = -7513379852268838992L;

  protected String image;
  // useful for some token type
  protected String content;

  public Token(String image2) throws ParseException {
    image = image2;
    parse();
  }

  public String getImage() {
    return image;
  }

  @Override
  public String toString() {
    return image;
  }

  protected abstract void parse() throws ParseException;

  public abstract int getType();

  static Token newToken(int tokenKind, String image2) throws ParseException {
    switch (tokenKind) {
    case TOKEN_FIXED:
      return new FixedToken(image2);
    case TOKEN_NOTE:
      return new NoteToken(image2);
    case TOKEN_ECHO:
      return new EchoToken(image2);
    case TOKEN_TAG:
      return new TagToken(image2);
    case TOKEN_MACRO:
      return new MacroToken(image2);
    default:
      throw new ParseException("Creating a token with unknown type >>> " + (char) tokenKind);
    }
  }

}
