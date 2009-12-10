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
package net.asfun.template.filter;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.Filter;
import net.asfun.template.compile.JangodCompiler;
import static net.asfun.template.util.logging.JangodLogger;

public class TruncateFilter implements Filter{

	@Override
	public Object filter(Object object, JangodCompiler compiler, String... arg) throws CompilerException {
		if ( object instanceof String ) {
			int length = 100;
			String ends = "...";
			if ( arg.length > 0 ) {
				try {
					length = Integer.valueOf(compiler.resolveString(arg[0]));
				} catch (Exception e) {
					JangodLogger.warning("filter truncate get length error use default >>> 100");
				}
			}
			if ( arg.length > 1 ) {
				ends = compiler.resolveString(arg[1]);
			}
			String string = (String)object;
			if ( string.length() > length ) {
				return string.substring(0, length) + ends;
			} else {
				return string;
			}
		}
		return object;
	}

	@Override
	public String getName() {
		return "truncate";
	}

}
