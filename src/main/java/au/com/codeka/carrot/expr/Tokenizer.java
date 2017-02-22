package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;

/**
 * Converts an input {@link Reader} into a stream of {@link Token}s.
 */
public class Tokenizer {
  private final Reader reader;
  @Nullable private Character lookahead;
  private Token token;
  private int line;
  private int col;

  public Tokenizer(Reader reader) throws CarrotException {
    this.reader = reader;
    line = 1;
    col = 1;
    next();
  }

  /**
   * Returns true if the current token if of the given {@link TokenType}. You can then use {@link #expect} to get
   * the token (and advance to the next one).
   *
   * @param type The {@link TokenType} we want to accept.
   * @return True, if the current token is of the given type, or false it's not.
   */
  public boolean accept(TokenType type) throws CarrotException {
    return (token.getType() == type);
  }

  /**
   * Returns a {@link Token} if it's of the given type, or throws a {@link CarrotException} if it's not.
   *
   * @param type The {@link TokenType} we want to accept.
   * @return The next {@link Token}, if it's of the given type.
   * @throws CarrotException If there's an error parsing the token, or if it's not of the given type.
   */
  @Nullable
  public Token expect(TokenType type) throws CarrotException {
    if (token.getType() == type) {
      Token t = token;
      next();
      return t;
    }
    throw new CarrotException("Expected token of type " + type + ", got " + token.getType(), line, col);
  }

  /** Advance to the {@link Token}, storing it in the member variable token. */
  private void next() throws CarrotException {
    int ch = nextChar();
    while (Character.isWhitespace(ch)) {
      ch = nextChar();
    }
    if (ch < 0) {
      token = new Token(TokenType.UNKNOWN);
      return;
    }


    int startLine = line;
    int startCol = col;
    int next;

    switch (ch) {
      case '(':
        token = new Token(TokenType.LPAREN);
        break;
      case ')':
        token = new Token(TokenType.RPAREN);
        break;
      case '[':
        token = new Token(TokenType.LSQUARE);
        break;
      case ']':
        token = new Token(TokenType.RSQUARE);
        break;
      case ',':
        token = new Token(TokenType.COMMA);
        break;
      case '.':
        token = new Token(TokenType.DOT);
        break;
      case '+':
        token = new Token(TokenType.PLUS);
        break;
      case '-':
        token = new Token(TokenType.MINUS);
        break;
      case '*':
        token = new Token(TokenType.MULTIPLY);
        break;
      case '/':
        token = new Token(TokenType.DIVIDE);
        break;
      case '=':
        next = nextChar();
        if (next != '=') {
          throw new CarrotException("Expected ==", startLine, startCol);
        }
        token = new Token(TokenType.EQUALITY);
        break;
      case '!':
        next = nextChar();
        if (next != '=') {
          throw new CarrotException("Expected !=", startLine, startCol);
        }
        token = new Token(TokenType.INEQUALITY);
        break;
      case '<':
        next = nextChar();
        if (next != '=') {
          if (next < 0) {
            throw new CarrotException("Unexpected end of file.", startLine, startCol);
          }
          lookahead = (char) next;
          token = new Token(TokenType.LESS_THAN);
        } else {
          token = new Token(TokenType.LESS_THAN_OR_EQUAL);
        }
        break;
      case '>':
        next = nextChar();
        if (next != '=') {
          if (next < 0) {
            throw new CarrotException("Unexpected end of file.", startLine, startCol);
          }
          lookahead = (char) next;
          token = new Token(TokenType.GREATER_THAN);
        } else {
          token = new Token(TokenType.GREATER_THAN_OR_EQUAL);
        }
        break;
      case '"':
      case '\'':
        String str = "";
        next = nextChar();
        while (next >= 0 && next != ch) {
          str += (char) next;
          next = nextChar();
        }
        if (next < 0) {
          throw new CarrotException("Unexpected end-of-file waiting for " + (char) ch, startLine, startCol);
        }
        token = new Token(TokenType.STRING_LITERAL, str);
        break;
      default:
        // if it starts with a number it's a number, else identifier.
        if ("0123456789".indexOf(ch) >= 0) {
          String number = "";
          number += (char) ch;
          next = nextChar();
          while (next >= 0 && "0123456789.".indexOf(next) >= 0) {
            number += (char) next;
            next = nextChar();
          }
          if (next >= 0) {
            lookahead = (char) next;
          }
          Object value;
          if (number.contains(".")) {
            value = Double.parseDouble(number);
          } else {
            value = Long.parseLong(number);
          }
          token = new Token(TokenType.NUMBER_LITERAL, value);
        } else if (Character.isJavaIdentifierStart(ch)) {
          String identifier = "";
          identifier += (char) ch;
          next = nextChar();
          while (next > 0 && Character.isJavaIdentifierPart(next)) {
            identifier += (char) next;
            next = nextChar();
          }
          if (next > 0) {
            lookahead = (char) next;
          }
          token = new Token(TokenType.IDENTIFIER, identifier);
        } else {
          throw new CarrotException("Unexpected character: " + (char) ch, startLine, startCol);
        }
    }
  }

  private int nextChar() throws CarrotException {
    try {
      int ch;
      if (lookahead != null) {
        ch = lookahead;
        lookahead = null;
      } else {
        ch = reader.read();
      }
      if (ch == '\n') {
        line++;
        col = 1;
      } else {
        col++;
      }

      return ch;
    } catch (IOException e) {
      throw new CarrotException(e);
    }
  }
}
