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

  /**
   * @return The {@link TokenType} of this token.
   */
  public TokenType getType() {
    return type;
  }

  /**
   * @return The contents of this token.
   */
  public String getContent() {
    return content;
  }

  /**
   * Create a new {@link Token}.
   *
   * @param type The {@link TokenType} of the token to create.
   * @param content The content to include in the token.
   * @return A new {@link Token}.
   */
  public static Token create(TokenType type, String content) {
    return new Token(type, content, null);
  }

  /**
   * Create a new {@link Token}.
   *
   * @param type The {@link TokenType} of the token to create.
   * @param content The content to include in the token.
   * @param ptr The {@link ResourcePointer} this token we created from (used when throwing exceptions later so we can
   *            know where the token was created from).
   * @return A new {@link Token}.
   */
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

  /**
   * @return The {@link ResourcePointer} from where this token was read from.
   */
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

