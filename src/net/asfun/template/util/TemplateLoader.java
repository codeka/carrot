/**********************************************************************
Copyright (c) 2009 Asfun Net.

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
package net.asfun.template.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class TemplateLoader {
	
	private String encoding = "utf-8";
	private String root = null;
	
	public void setBase(String rootPath) {
		if ( rootPath == null) return;
		if ( ! rootPath.endsWith(File.separator) ) {
			root = rootPath + File.separator;
		} else {
			root = rootPath;
		}
	}
	
	public void setEncoding(String enc) {
		encoding = enc;
	}
	
	public Reader getReader(String fileName) throws IOException {
		return getReader(fileName, encoding);
	}
	
	public Reader getReader(String fileName, String encoding) throws IOException {
		if ( root != null ) {
			fileName = root + fileName;
		}
		try {
			return new InputStreamReader(new FileInputStream(fileName),encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IOException("Unsupported encoding >>> " + encoding);
		} catch (FileNotFoundException e) {
			throw new IOException(e.getMessage());
		}
	}
	
}
