package au.com.codeka.carrot.parse;

/**
 * Factory for creating {@link Token}s.
 */
public interface TokenFactory {
  public Token create(TokenType type, StringBuilder content);
}
