package au.com.codeka.carrot.parse;

import static au.com.codeka.carrot.parse.ParserConstants.*;

import au.com.codeka.carrot.base.Constants;

public class TagToken extends Token {

  private static final long serialVersionUID = 2766011408032384360L;

  private String tagName;
  private String helpers;

  public TagToken(String image) throws ParseException {
    super(image);
  }

  @Override
  public int getType() {
    return TOKEN_TAG;
  }

  /**
   * Get tag name
   */
  @Override
  protected void parse() {
    content = image.substring(2, image.length() - 2).trim()
        .replaceFirst(SCPACE_STR, Constants.STR_SPACE);
    int postBlank = content.indexOf(SP);
    if (postBlank > 0) {
      tagName = content.substring(0, postBlank).toLowerCase();
      helpers = content.substring(postBlank).trim();
    }
    else {
      tagName = content.toLowerCase();
      helpers = "";
    }
  }

  public String getTagName() {
    return tagName;
  }

  public String getHelpers() {
    return helpers;
  }

  @Override
  public String toString() {
    if (helpers.length() == 0) {
      return "{% " + tagName + " %}";
    }
    return "{% " + tagName + " " + helpers + " %}";
  }

}
