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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileLoader implements ResourceLoader{

	Configuration config;

	@Override
	public Reader getReader(String fileName, String encoding, String directory)
			throws IOException {
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			return new InputStreamReader(new FileInputStream(file), encoding);
		} else {
			if ( directory != null ) {
				file = new File(directory + fileName);
				if (file.exists() && file.isFile()) {
					return new InputStreamReader(new FileInputStream(file), encoding);
				} 
			}
			file = new File(config.getWorkspace() + fileName);
			if (file.exists() && file.isFile()) {
				return new InputStreamReader(new FileInputStream(file), encoding);
			}
		}
		throw new IOException("File not found >>> " + fileName);
	}

	@Override
	public String getString(String fileName, String encoding, String directory)
			throws IOException {
		Reader reader = getReader(fileName, encoding, directory);
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
	public String getDirectory(String fileName, String directory) {
		File file = new File(fileName);
		if ( directory != null ) {
			if ( ! file.exists() ) {
				file = new File(directory + fileName);
				if ( !file.exists() ) {
					file = new File(config.getWorkspace() + fileName);
				}
			}
			if ( file.exists() ) {
				try {
					String path = file.getCanonicalPath();
					return path.substring(0, path.lastIndexOf(File.separatorChar) + 1);
				} catch (IOException e) {
					return directory;
				}
			}
			return directory;
		} else {
			if ( ! file.exists() ) {
				file = new File(config.getWorkspace() + fileName);
			}
			if ( file.exists() ) {
				try {
					String path = file.getCanonicalPath();
					return path.substring(0, path.lastIndexOf(File.separatorChar) + 1);
				} catch (IOException e) {
					return config.getWorkspace();
				}
			}
			return config.getWorkspace();
		}
	}

	@Override
	public void setConfiguration(Configuration config) {
		this.config = config;
	}

	@Override
	public Reader getReader(String fileName, String directory)
			throws IOException {
		return getReader(fileName, config.getEncoding(), directory);
	}

	@Override
	public String getString(String fileName, String directory)
			throws IOException {
		return getString(fileName, config.getEncoding(), directory);
	}

}
