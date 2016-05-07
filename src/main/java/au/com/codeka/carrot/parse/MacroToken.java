package au.com.codeka.carrot.parse;

import static au.com.codeka.carrot.parse.ParserConstants.*;

import au.com.codeka.carrot.base.Constants;

/**
 * Do something hard to be done by TagToken
 * 
 * @author anysome
 *
 */
public class MacroToken extends Token {

  private static final long serialVersionUID = -3710981054298651807L;

  private String macroName;
  private String helpers;

  public MacroToken(String image) throws ParseException {
    super(image);
  }

  @Override
  public int getType() {
    return TOKEN_MACRO;
  }

  @Override
  protected void parse() throws ParseException {
    content = image.substring(2, image.length() - 2).trim()
        .replaceFirst(SCPACE_STR, Constants.STR_SPACE);
    int postBlank = content.indexOf(SP);
    if (postBlank > 0) {
      macroName = content.substring(0, postBlank).toLowerCase();
      helpers = content.substring(postBlank).trim();
    }
    else {
      macroName = content;
      helpers = "";
    }
  }

  public String getMacroName() {
    return macroName;
  }

  public String getHelpers() {
    return helpers;
  }

  @Override
  public String toString() {
    if (helpers.length() == 0) {
      return "{! " + macroName + " !}";
    }
    return "{! " + macroName + " " + helpers + " !}";
  }

}
