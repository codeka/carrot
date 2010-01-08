package net.asfun.template.filter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.Filter;
import net.asfun.template.compile.JangodCompiler;
import static net.asfun.template.util.logging.JangodLogger;

public class Md5Filter implements Filter {

	private final static String[] NOSTR = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f" };

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

	public String md5(String str) {
		String result = null;
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			result = byteToString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			JangodLogger.severe(ex.getMessage());
		}

		return result;
	}

	@Override
	public Object filter(Object object, JangodCompiler compiler, String... arg)
			throws CompilerException {
		if (object instanceof String) {
			return md5((String) object);
		}
		return object;
	}

	@Override
	public String getName() {
		return "md5";
	}

}
