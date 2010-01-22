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

import java.io.IOException;
import java.io.Reader;

public interface ResourceLoader {

	public String getRoot();
	
	public void setRoot(String root);
	
	public String getEncoding();
	
	public void setEncoding(String encoding);
	
	public Reader getReader(String fileName) throws IOException;
	
	public Reader getReader(String fileName, String encoding) throws IOException;
	
	public String getString(String fileName) throws IOException;
	
	public String getString(String fileName, String encoding) throws IOException;
	
}
