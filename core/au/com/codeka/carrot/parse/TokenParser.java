package au.com.codeka.carrot.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.NoSuchElementException;

import au.com.codeka.carrot.base.Constants;

/** Essentially an iterator for iterating over tokens in the input stream. */
public class TokenParser {

  private Tokenizer tm = new Tokenizer();
  private Token token;
  private boolean proceeding = true;

  public TokenParser(String text) {
    tm.init(text);
  }

  public TokenParser(Reader reader) throws ParseException {
    init(reader);
  }

  public void init(String text) {
    tm.init(text);
    token = null;
    proceeding = true;
  }

  public void init(Reader reader) throws ParseException {
    BufferedReader br = new BufferedReader(reader);
    StringBuffer buff = new StringBuffer();
    String line;
    try {
      while ((line = br.readLine()) != null) {
        buff.append(line);
        buff.append(Constants.STR_NEW_LINE);
      }
    } catch (IOException e) {
      throw new ParseException("read template reader fault.", e.getCause());
    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        throw new ParseException("read template reader fault.", e.getCause());
      }
    }
    tm.init(buff.toString());
    token = null;
    proceeding = true;
  }

  public boolean hasNext() throws ParseException {
    if (proceeding) {
      token = tm.getNextToken();
      if (token != null) {
        return true;
      } else {
        proceeding = false;
        return false;
      }
    }
    return false;
  }

  public Token next() throws ParseException {
    if (proceeding) {
      if (token == null) {
        Token tk = tm.getNextToken();
        if (tk == null) {
          proceeding = false;
          throw new NoSuchElementException();
        }
        return tk;
      } else {
        Token last = token;
        token = null;
        return last;
      }
    }
    throw new NoSuchElementException();
  }
}
