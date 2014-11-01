package au.com.codeka.carrot.lib.filter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import au.com.codeka.carrot.interpret.InterpretException;
import au.com.codeka.carrot.interpret.JangodInterpreter;
import au.com.codeka.carrot.lib.Filter;

public class Md5Filter implements Filter {

  final String[] NOSTR = { "0", "1", "2", "3", "4", "5", "6",
      "7", "8", "9", "a", "b", "c", "d", "e", "f" };
  final String md5 = "MD5";

  private String byteToArrayString(byte bByte) {
    int temp = bByte;
    if (temp < 0) {
      temp += 256;
    }
    int iD1 = temp / 16;
    int iD2 = temp % 16;
    return NOSTR[iD1] + NOSTR[iD2];
  }

  private String byteToString(byte[] bByte) {
    StringBuffer sBuffer = new StringBuffer();
    for (int i = 0; i < bByte.length; i++) {
      sBuffer.append(byteToArrayString(bByte[i]));
    }
    return sBuffer.toString();
  }

  public String md5(String str) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(md5);
    return byteToString(md.digest(str.getBytes()));
  }

  @Override
  public Object filter(Object object, JangodInterpreter interpreter, String... arg)
      throws InterpretException {
    try {
      if (object instanceof String) {
        return md5((String) object);
      }
    } catch (NoSuchAlgorithmException e) {
      throw new InterpretException(e);
    }
    return object;
  }

  @Override
  public String getName() {
    return "md5";
  }

}
