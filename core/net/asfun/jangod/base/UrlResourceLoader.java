/**********************************************************************
Copyright (c) 2010 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.jangod.base;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlResourceLoader implements ResourceLoader{
	
	static final String NEW_LINE = "\n";
	
	String root;
	String encoding;
	
	public String getRoot() {
		return root;
	}

	public String getEncoding() {
		return encoding;
	}

	public Reader getReader(String fileName) throws IOException {
		return getReader(fileName, encoding);
	}
	
	public Reader getReader(String fileName, String encoding) throws IOException {
		if ( root != null ) {
			fileName = root + fileName;
		}
		try {
			URL urlFile = null;
			try {
				urlFile = new URL(fileName);
			} catch (MalformedURLException e) {
				urlFile = Configuration.class.getClassLoader().getResource(fileName);
			}
			if (urlFile == null) {
				File f = new File(fileName);
				if (f.exists() && f.isFile()) {
					try {
						urlFile = f.toURI().toURL();
					} catch (MalformedURLException e) {
						JangodLogger.warning("Can't find file >>> " + fileName);
					}
				} else {
					JangodLogger.warning("Can't find file >>> " + fileName);
				}
			}
			return new InputStreamReader(urlFile.openStream(), encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IOException("Unsupported encoding >>> " + encoding);
		} catch (FileNotFoundException e) {
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public String getString(String fileName) throws IOException {
		return getString(fileName, encoding);
	}

	@Override
	public String getString(String fileName, String encoding)
			throws IOException {
		Reader reader = getReader(fileName, encoding);
		BufferedReader br = new BufferedReader(reader);
		StringBuffer buff = new StringBuffer();
		String line;
		try {
			while( (line=br.readLine()) != null ) {
				buff.append(line);
				buff.append(NEW_LINE);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			reader.close();
		}
		return buff.toString();
	}

	@Override
	public void setEncoding(String encoding) {
		if ( encoding == null ) {
			throw new NullPointerException("encoding can not be null.");
		}
		this.encoding = encoding;
	}

	@Override
	public void setRoot(String root) {
		this.root = root;
	}
	
}
