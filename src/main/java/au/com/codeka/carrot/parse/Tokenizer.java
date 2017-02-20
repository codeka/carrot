package au.com.codeka.carrot.parse;

import au.com.codeka.carrot.CarrotException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

/**
 * The {@link Tokenizer} takes an input stream of character and turns it into a stream of {@link Token}s.
 *
 * <p>Each {@link Token} represents a high-level component of the template. For example, the following template:
 *
 * <p><code>Text {{ hello }} stuff {% if (blah) { %} stuff {% } %}</code>
 *
 * <p>Corresponds to the following stream of tokens:
 *
 * <p><code>FixedToken: content="Text "
 * EchoToken: content="hello"
 * FixedToken: content="stuff"
 * TagToken: content="if (blah) {"
 * FixedToken: content=" stuff "
 * TagToken: content="}"</code>
 */
public class Tokenizer {
  private final Reader ins;
  private final TokenFactory tokenFactory;

  private char[] lookahead;
  private int line;
  private int col;

  public Tokenizer(Reader ins) {
    this(ins, null);
  }

  public Tokenizer(Reader ins, @Nullable TokenFactory tokenFactory) {
    this.ins = ins;
    this.tokenFactory = tokenFactory == null ? new DefaultTokenFactory() : tokenFactory;
    this.line = 1;
  }

  /**
   * Gets the next token from the stream, or null if there's no tokens left.
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
                throw new CarrotException("Unexpected '{%'", line, col);
              }
              break;
            case '{':
              if (tokenType == TokenType.UNKNOWN) {
                tokenType = TokenType.ECHO;
              } else if (tokenType == TokenType.FIXED) {
                lookahead = new char[]{'{', '{'};
                return tokenFactory.create(tokenType, content);
              } else {
                throw new CarrotException("Unexpected '{{", line, col);
              }
              break;
            case '#':
              if (tokenType == TokenType.UNKNOWN) {
                tokenType = TokenType.COMMENT;
              } else if (tokenType == TokenType.FIXED) {
                lookahead = new char[]{'{', '#'};
                return tokenFactory.create(tokenType, content);
              } else {
                throw new CarrotException("Unexpected '{{", line, col);
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
            return tokenFactory.create(tokenType, content);
          }

          if ((char) i == '}') {
            if (tokenType == TokenType.ECHO && ch != '}') {
              throw new CarrotException("Expected '}}'", line, col);
            } else if (tokenType == TokenType.TAG && ch != '%') {
              throw new CarrotException("Expected '%}'", line, col);
            } else if (tokenType == TokenType.COMMENT && ch != '#') {
              throw new CarrotException("Expected '#}'", line, col);
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
      int ch = ins.read();
      if (ch == '\n') {
        line ++;
        col = 0;
      } else {
        col ++;
      }
      return ch;
    } catch (IOException e) {
      throw new CarrotException(e);
    }
  }

  private class DefaultTokenFactory implements TokenFactory {
    @Override
    public Token create(TokenType type, StringBuilder content) {
      switch (type) {
        case UNKNOWN:
          return null;
        default:
          return Token.create(type, content.toString(), line, col);
      }
    }
  }
}
