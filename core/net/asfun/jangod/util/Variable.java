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
package net.asfun.jangod.util;

import java.util.Arrays;
import java.util.List;

public class Variable {

	private String name;
	private List<String> chainList;
	
	public Variable(String variable) {
		split(variable);
	}

	private void split(String variable) {
		if (!variable.contains(".")) {
			name = variable;
			chainList = null;
			return;
		}
		
		String[] parts = variable.split("\\.");
		name = parts[0];
		chainList = Arrays.asList(parts);
		chainList = chainList.subList(1, chainList.size());
		
	}

	public String getName() {
		return name;
	}

	public Object resolve(Object value) {
		if ( chainList != null ) {
			return new VariableChain(chainList, value).resolve();
		} else {
			return value;
		}
	}
	
	@Override
	public String toString() {
		return "<Variable: " + name + ">";
	}
}
