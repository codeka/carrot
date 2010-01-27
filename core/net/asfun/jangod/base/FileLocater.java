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

public class FileLocater implements ResourceLocater {

	@Override
	public String getDirectory(String fullName) throws IOException {
		File file = new File(fullName);
		if ( file.isFile() ) {
			return file.getParentFile().getCanonicalPath();
		}
		return file.getCanonicalPath();
	}

	@Override
	public String getFullName(String relativeName, String relativeDir, String defaultDir) throws IOException {
		File file = new File(relativeName);
		if (file.exists() && file.isFile()) {
			return file.getCanonicalPath();
		}
		file = new File(relativeDir + File.separator + relativeName);
		if (file.exists() && file.isFile()) {
			return file.getCanonicalPath();
		}
		file = new File(defaultDir + File.separator + relativeName);
		if (file.exists() && file.isFile()) {
			return file.getCanonicalPath();
		}
		throw new IOException("File not found >>> '" + relativeName + "' in "
				+ relativeDir + " and " + defaultDir);
	}

	@Override
	public String getFullName(String relativeName, String defaultDir) throws IOException {
		File file = new File(relativeName);
		if (file.exists() && file.isFile()) {
			return file.getCanonicalPath();
		}
		file = new File(defaultDir + File.separator + relativeName);
		if (file.exists() && file.isFile()) {
			return file.getCanonicalPath();
		}
		throw new IOException("File not found >>> '" + relativeName + "' in " + defaultDir);
	}

	@Override
	public Reader getReader(String fullName, String encoding) throws IOException {
		File file = new File(fullName);
		if (file.exists() && file.isFile()) {
			return new InputStreamReader(new FileInputStream(file), encoding);
		}
		throw new IOException("File not found >>> " + fullName);
	}

	@Override
	public String getString(String fullName, String encoding) throws IOException {
		Reader reader = getReader(fullName, encoding);
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

}
