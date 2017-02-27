package au.com.codeka.carrot.tmpl.parse;

import au.com.codeka.carrot.resource.ResourcePointer;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Represents a token in a stream of tokens from the {@link Tokenizer}.
 */
public class Token {
  private final TokenType type;
  private final String content;
  @Nullable private final ResourcePointer ptr;

  private Token(TokenType type, String content, ResourcePointer ptr) {
    this.type = type;
    this.content = content;
    this.ptr = ptr;
  }

  public TokenType getType() {
    return type;
  }

  public String getContent() {
    return content;
  }

  public static Token create(TokenType type, String content) {
    return new Token(type, content, null);
  }

  public static Token create(TokenType type, String content, ResourcePointer ptr) {
    return new Token(type, content, ptr);
  }

  @Override
  public boolean equals(Object other) {
    if (other instanceof Token) {
      return ((Token) other).type == type
          && ((Token) other).content.equals(content);
    }
    return false;
  }

  /** Gets the {@link ResourcePointer} from where this token was read from. */
  public ResourcePointer getPointer() {
    return ptr;
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

