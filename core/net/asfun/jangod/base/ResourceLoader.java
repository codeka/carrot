package net.asfun.jangod.base;

import java.io.IOException;
import java.io.Reader;

public interface ResourceLoader {

	public String getRoot();
	public String getEncoding();
	public Reader getReader(String fileName) throws IOException;
	public Reader getReader(String fileName, String encoding) throws IOException;
}
