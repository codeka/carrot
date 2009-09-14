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

import java.math.BigDecimal;
import java.math.BigInteger;

import net.asfun.template.compile.CompilerException;
import net.asfun.template.compile.Filter;
import net.asfun.template.compile.JangodCompiler;

public class AbsFilter implements Filter{

	@Override
	public Object filter(Object object, JangodCompiler compiler, String... arg) throws CompilerException {
		if ( object instanceof Integer ) {
			return Math.abs((Integer)object);
		}
		if ( object instanceof Float ) {
			return Math.abs((Float)object);
		}
		if ( object instanceof Long ) {
			return Math.abs((Long)object);
		}
		if ( object instanceof Short ) {
			return Math.abs((Short)object);
		}
		if ( object instanceof Double ) {
			return Math.abs((Double)object);
		}
		if ( object instanceof BigDecimal ) {
			return ((BigDecimal)object).abs();
		}
		if ( object instanceof BigInteger ) {
			return ((BigInteger)object).abs();
		}
		if ( object instanceof Byte ) {
			return Math.abs((Byte)object);
		}
		if ( object instanceof String ) {
			try {
				return new BigDecimal(object.toString()).abs();
			} catch (Exception e) {
				throw new CompilerException(object + " can't be dealed with abs filter");
			}
		}
		return object;
	}

	@Override
	public String getName() {
		return "abs";
	}

}
