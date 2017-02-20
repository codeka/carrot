package au.com.codeka.carrot.parse;

import java.util.Objects;

/**
 * Represents a token in a stream of tokens from the {@link Tokenizer}.
 */
public class Token {
  private final TokenType type;
  private final String content;
  private final int line;
  private final int column;

  private Token(TokenType type, String content, int line, int column) {
    this.type = type;
    this.content = content;
    this.line = line;
    this.column = column;
  }

  public TokenType getType() {
    return type;
  }

  public String getContent() {
    return content;
  }

  public static Token create(TokenType type, String content) {
    return new Token(type, content, 0, 0);
  }

  public static Token create(TokenType type, String content, int line, int column) {
    return new Token(type, content, line, column);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Token) {
      return ((Token) other).type == type
          && ((Token) other).content.equals(content);
    }
    return false;
  }

  /** Gets the line this token appears on. */
  public int getLine() {
    return line;
  }

  /** Gets the column this token appears on. */
  public int getColumn() {
    return column;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, content);
  }

  @Override
  public String toString() {
    return String.format("%s <%s>", type, content);
  }
}

