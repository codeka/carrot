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

public class MultiplyFilter implements Filter{

	@Override
	public Object filter(Object object, JangodCompiler compiler, String... arg)
			throws CompilerException {
		if ( arg.length != 1) {
			throw new CompilerException("filter multiply expects 1 arg >>> " + arg.length);
		}
		Object toMul = compiler.resolveObject(arg[0]);
		Number num;
		if ( toMul instanceof String ) {
			num = new BigDecimal(toMul.toString());
		} else if ( toMul instanceof Number ) {
			num = (Number) toMul;
		} else {
			return object;
		}
		if ( object instanceof Integer ) {
			return 0L + num.intValue() * (Integer)object;
		}
		if ( object instanceof Float ) {
			return 0D + num.floatValue() * (Float)object;
		}
		if ( object instanceof Long ) {
			return num.longValue() * (Long)object;
		}
		if ( object instanceof Short ) {
			return 0 + num.shortValue() * (Short)object;
		}
		if ( object instanceof Double ) {
			return num.doubleValue() * (Double)object;
		}
		if ( object instanceof BigDecimal ) {
			return ((BigDecimal)object).multiply(BigDecimal.valueOf(num.doubleValue()));
		}
		if ( object instanceof BigInteger ) {
			return ((BigInteger)object).multiply(BigInteger.valueOf(num.longValue()));
		}
		if ( object instanceof Byte ) {
			return num.byteValue() * (Byte)object;
		}
		if ( object instanceof String ) {
			try {
				return num.doubleValue() * Double.valueOf(object.toString());
			} catch (Exception e) {
				throw new CompilerException(object + " can't be dealed with multiply filter");
			}
		}
		return object;
	}

	@Override
	public String getName() {
		return "multiply";
	}

}
