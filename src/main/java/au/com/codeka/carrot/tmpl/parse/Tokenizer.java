package au.com.codeka.carrot.tmpl.parse;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.resource.ResourcePointer;
import au.com.codeka.carrot.util.LineReader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Arrays;

/**
 * The {@link Tokenizer} takes an input stream of character and turns it into a stream of {@link Token}s.
 *
 * <p>Each {@link Token} represents a high-level component of the template. For example, the following template:
 *
 * <p><code>Text {{ hello }} stuff {% if (blah) %} more stuff {% end %}</code>
 *
 * <p>Corresponds to the following stream of tokens:
 *
 * <pre><code>TokenType=FIXED, Content="Text "
 *TokenType=ECHO, Content=" hello "
 *TokenType=FIXED, Content=" stuff "
 *TokenType=TAG, Content=" if (blah) "
 *TokenType=FIXED, Content=" more stuff "
 *TokenType=TAG, Content=" end "</code></pre>
 */
public class Tokenizer {
  private final LineReader ins;
  private final TokenFactory tokenFactory;

  private char[] lookahead;

  /**
   * Construct a new {@link Tokenizer} with the given {@link LineReader}, and a default {@link TokenFactory}.
   *
   * @param ins A {@link LineReader} to read tokens from.
   */
  public Tokenizer(LineReader ins) {
    this(ins, null);
  }

  /**
   * Construct a new {@link Tokenizer} with the given {@link LineReader} and {@link TokenFactory}.
   *
   * @param ins A {@link LineReader} to read tokens from.
   * @param tokenFactory A {@link TokenFactory} for creating the tokens. If null, a default token factory that just
   *                     creates instances of {@link Token} is used.
   */
  public Tokenizer(LineReader ins, @Nullable TokenFactory tokenFactory) {
    this.ins = ins;
    this.tokenFactory = tokenFactory == null ? new DefaultTokenFactory() : tokenFactory;
  }

  /**
   * Gets the next token from the stream, or null if there's no tokens left.
   *
   * @return The next {@link Token} in the stream, or null if we're at the end of the stream.
   * @throws CarrotException when there's an error parsing the tokens.
   */
  @Nullable
  public Token getNextToken() throws CarrotException {
    TokenType tokenType = TokenType.UNKNOWN;
    StringBuilder content = new StringBuilder();

    while (true) {
      int i = getNextChar();
      if (i < 0) {
        return tokenFactory.create(tokenType, content);
      }
      char ch = (char) i;

      switch (ch) {
        case '{':
          i = getNextChar();
          if (i < 0) {
            content.append(ch);
            return tokenFactory.create(tokenType, content);
          }

          switch (i) {
            case '%':
              if (tokenType == TokenType.UNKNOWN) {
                tokenType = TokenType.TAG;
              } else if (tokenType == TokenType.FIXED) {
                lookahead = new char[]{'{', '%'};
                return tokenFactory.create(tokenType, content);
              } else {
                throw new CarrotException("Unexpected '{%'", ins.getPointer());
              }
              break;
            case '{':
              if (tokenType == TokenType.UNKNOWN) {
                tokenType = TokenType.ECHO;
              } else if (tokenType == TokenType.FIXED) {
                lookahead = new char[]{'{', '{'};
                return tokenFactory.create(tokenType, content);
              } else {
                throw new CarrotException("Unexpected '{{", ins.getPointer());
              }
              break;
            case '#':
              if (tokenType == TokenType.UNKNOWN) {
                tokenType = TokenType.COMMENT;
              } else if (tokenType == TokenType.FIXED) {
                lookahead = new char[]{'{', '#'};
                return tokenFactory.create(tokenType, content);
              } else {
                throw new CarrotException("Unexpected '{{", ins.getPointer());
              }
              break;
            case '\\':
            default:
              if (tokenType == TokenType.UNKNOWN) {
                tokenType = TokenType.FIXED;
              }
              content.append(ch);
              // if it's a '\\' we just eat the backslash, it's an escape character
              if (i != '\\') {
                content.append((char) i);
              }
          }
          break;
        case '%':
        case '}':
        case '#':
          i = getNextChar();
          if (i < 0) {
            content.append(ch);
            return tokenFactory.create(tokenType, content);
          }

          if ((char) i == '}') {
            if (tokenType == TokenType.ECHO && ch != '}') {
              throw new CarrotException("Expected '}}'", ins.getPointer());
            } else if (tokenType == TokenType.TAG && ch != '%') {
              throw new CarrotException("Expected '%}'", ins.getPointer());
            } else if (tokenType == TokenType.COMMENT && ch != '#') {
              throw new CarrotException("Expected '#}'", ins.getPointer());
            } else if (tokenType == TokenType.FIXED) {
              content.append(ch);
              content.append((char) i);
            } else {
              return tokenFactory.create(tokenType, content);
            }
          } else {
            content.append(ch);
            content.append((char) i);
          }
          break;
        default:
          if (tokenType == TokenType.UNKNOWN) {
            tokenType = TokenType.FIXED;
          }
          content.append(ch);
          break;
      }
    }
  }

  /** @return The current {@link ResourcePointer}, useful for outputting where in the file an error occurred. */
  public ResourcePointer getPointer() {
    return ins.getPointer();
  }

  private int getNextChar() throws CarrotException {
    if (lookahead != null) {
      char ch = lookahead[0];
      if (lookahead.length == 1) {
        lookahead = null;
      } else {
        lookahead = Arrays.copyOfRange(lookahead, 1, lookahead.length);
      }
      return ch;
    }

    try {
      return ins.nextChar();
    } catch (IOException e) {
      throw new CarrotException(e, ins.getPointer());
    }
  }

  private class DefaultTokenFactory implements TokenFactory {
    @Override
    public Token create(TokenType type, StringBuilder content) {
      switch (type) {
        case UNKNOWN:
          return null;
        default:
          return Token.create(type, content.toString(), ins.getPointer());
      }
    }
  }
}
