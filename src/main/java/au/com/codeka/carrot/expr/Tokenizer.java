package au.com.codeka.carrot.expr;

import au.com.codeka.carrot.CarrotException;
import au.com.codeka.carrot.util.LineReader;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayDeque;

/**
 * Converts an input {@link Reader} into a stream of {@link Token}s.
 */
public class Tokenizer {
  private final LineReader reader;
  @Nullable
  private Character lookahead;
  private ArrayDeque<Token> tokens = new ArrayDeque<>();

  public Tokenizer(LineReader reader) throws CarrotException {
    this.reader = reader;
    next();
  }

  /**
   * Returns true if the current token is and of the given {@link TokenType}s. You can then use {@link #expect} to get
   * the token (and advance to the next one).
   *
   * @param types The {@link TokenType} we want to accept.
   * @return True, if the current token is of the given type, or false it's not.
   * @throws CarrotException If there's an error parsing the tokens.
   */
  public boolean accept(TokenType... types) throws CarrotException {
    Token token = tokens.peek();
    for (int i = 0; i < types.length; i++) {
      if (types[i] == token.getType()) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns true if the token at the given offset from the current is of the given {@link TokenType}. The 0th token
   * is the current one, the 1st is the after that and so on. This can be used to "look ahead" into the token stream.
   *
   * @param offset The offset from "current" that we want to peek. 0 is the current token, 1 is the next and so on.
   * @param type   The {@link TokenType} we want to accept.
   * @return True, if the current token is of the given type, or false it's not.
   * @throws CarrotException If there's an error parsing the tokens.
   */
  public boolean accept(int offset, TokenType type) throws CarrotException {
    if (offset == 0) {
      return accept(type);
    }

    while (tokens.size() <= offset) {
      next();
    }
    Token[] array = tokens.toArray(new Token[tokens.size()]);
    return (array[offset].getType() == type);
  }

  /**
   * Returns a {@link Token} if it's one of the given types, or throws a {@link CarrotException} if it's not.
   *
   * @param types The {@link TokenType}s we want to accept one of.
   * @return The next {@link Token}, if it's of the given type.
   * @throws CarrotException If there's an error parsing the token, or if it's not of the given type.
   */
  @Nullable
  public Token expect(TokenType... types) throws CarrotException {
    for (TokenType type : types) {
      if (tokens.peek().getType() == type) {
        Token t = tokens.remove();
        next();
        return t;
      }
    }

    StringBuilder typeString = new StringBuilder();
    for (int i = 0; i < types.length; i++) {
      if (i > 0) {
        if (i == types.length - 1) {
          typeString.append(" or ");
        } else {
          typeString.append(", ");
        }
      }
      typeString.append(types[i]);
    }
    throw new CarrotException(
        "Expected token of type " + typeString + ", got " + tokens.peek().getType(), reader.getPointer());
  }

  /**
   * @throws CarrotException unless we're at the end of the tokens.
   */
  public void end() throws CarrotException {
    expect(TokenType.EOF);
  }

  /**
   * Creates a {@link CarrotException} with the given message, populated with our current state.
   *
   * @param msg The message to create the exception with.
   * @return A {@link CarrotException} with the given message (presumably because we got an unexpected token).
   */
  public CarrotException unexpected(String msg) {
    return new CarrotException(String.format("%s, found: %s", msg, tokens.peek()), reader.getPointer());
  }

  /**
   * Advance to the {@link Token}, storing it in the member variable token.
   *
   * @throws CarrotException if there's an error parsing the tokens.
   */
  private void next() throws CarrotException {
    int ch = nextChar();
    while (Character.isWhitespace(ch)) {
      ch = nextChar();
    }
    if (ch < 0) {
      tokens.add(new Token(TokenType.EOF));
      return;
    }

    int next;
    Token token;

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
      case '&':
        next = nextChar();
        if (next != '&') {
          throw new CarrotException("Expected &&", reader.getPointer());
        }
        token = new Token(TokenType.LOGICAL_AND);
        break;
      case '|':
        next = nextChar();
        if (next != '|') {
          throw new CarrotException("Expected ||", reader.getPointer());
        }
        token = new Token(TokenType.LOGICAL_OR);
        break;
      case '=':
        next = nextChar();
        if (next != '=') {
          if (next > 0) {
            lookahead = (char) next;
          }
          token = new Token(TokenType.ASSIGNMENT);
        } else {
          token = new Token(TokenType.EQUALITY);
        }
        break;
      case '!':
        next = nextChar();
        if (next != '=') {
          lookahead = (char) next;
          token = new Token(TokenType.NOT);
        } else {
          token = new Token(TokenType.INEQUALITY);
        }
        break;
      case '<':
        next = nextChar();
        if (next != '=') {
          if (next > 0) {
            lookahead = (char) next;
          }
          token = new Token(TokenType.LESS_THAN);
        } else {
          token = new Token(TokenType.LESS_THAN_OR_EQUAL);
        }
        break;
      case '>':
        next = nextChar();
        if (next != '=') {
          if (next > 0) {
            lookahead = (char) next;
          }
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
          throw new CarrotException("Unexpected end-of-file waiting for " + (char) ch, reader.getPointer());
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
          switch (identifier) {
            case "or":
              token = new Token(TokenType.LOGICAL_OR, identifier);
              break;
            case "and":
              token = new Token(TokenType.LOGICAL_AND, identifier);
              break;
            case "not":
              token = new Token(TokenType.NOT, identifier);
              break;
            case "in":
              token = new Token(TokenType.IN, identifier);
              break;
            default:
              token = new Token(TokenType.IDENTIFIER, identifier);
          }
        } else {
          throw new CarrotException("Unexpected character: " + (char) ch, reader.getPointer());
        }
    }

    tokens.add(token);
  }

  private int nextChar() throws CarrotException {
    try {
      int ch;
      if (lookahead != null) {
        ch = lookahead;
        lookahead = null;
      } else {
        ch = reader.nextChar();
      }

      return ch;
    } catch (IOException e) {
      throw new CarrotException(e);
    }
  }
}
