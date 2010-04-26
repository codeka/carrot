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
package net.asfun.jangod.lib.filter;

import java.math.BigDecimal;
import java.math.BigInteger;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;

public class AddFilter implements Filter{

	@Override
	public Object filter(Object object, JangodInterpreter interpreter, String... arg)
			throws InterpretException {
		if ( arg.length != 1) {
			throw new InterpretException("filter add expects 1 arg >>> " + arg.length);
		}
		Object toAdd = interpreter.resolveObject(arg[0]);
		Number num;
		if ( toAdd instanceof String ) {
			try { 
				num = new BigDecimal(toAdd.toString());
			} catch (Exception e) {
				throw new InterpretException("filter add arg can't cast to number >>> " + toAdd);
			}
		} else if ( toAdd instanceof Number ) {
			num = (Number) toAdd;
		} else {
			return object;
		}
		if ( object instanceof Integer ) {
			return 0L + num.intValue() + (Integer)object;
		}
		if ( object instanceof Float ) {
			return 0D + num.floatValue() + (Float)object;
		}
		if ( object instanceof Long ) {
			return num.longValue() + (Long)object;
		}
		if ( object instanceof Short ) {
			return 0 + num.shortValue() + (Short)object;
		}
		if ( object instanceof Double ) {
			return num.doubleValue() + (Double)object;
		}
		if ( object instanceof BigDecimal ) {
			return ((BigDecimal)object).add(BigDecimal.valueOf(num.doubleValue()));
		}
		if ( object instanceof BigInteger ) {
			return ((BigInteger)object).add(BigInteger.valueOf(num.longValue()));
		}
		if ( object instanceof Byte ) {
			return num.byteValue() + (Byte)object;
		}
		if ( object instanceof String ) {
			try {
				String sv = (String) object;
				if ( sv.contains(".") ) {
					return num.doubleValue() + Double.valueOf(sv);
				} else {
					return num.longValue() + Long.valueOf(sv);
				}
			} catch (Exception e) {
				throw new InterpretException(object + " can't be dealed with add filter");
			}
		}
		return object;
	}

	@Override
	public String getName() {
		return "add";
	}

}
