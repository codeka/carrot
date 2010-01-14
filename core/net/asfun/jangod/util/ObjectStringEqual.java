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

import java.math.BigDecimal;
import java.math.BigInteger;

public class ObjectStringEqual {

	public static boolean evaluate(Object object, Object strObj) {
		if ( object == null ) {
			return strObj == null;
		} else {
			if ( strObj == null ) return false;
			if (String.class.isAssignableFrom(strObj.getClass())) {
				String str = (String)strObj;
				if ( object instanceof String ) {
					return str.equals(object);
				}
				
				if ( object instanceof Integer ) {
					try {
						return Integer.valueOf(str).equals(object);
					} catch (Exception e) {
						return false;
					}
				}
				
				if ( object instanceof Long ) {
					try {
						return Long.valueOf(str).equals(object);
					} catch (Exception e) {
						return false;
					}
				}
				
				if ( object instanceof Boolean ) {
					if ( (Boolean)object ) {
						return str.equalsIgnoreCase("True");
					} else {
						return str.equalsIgnoreCase("False");
					}
				}
				
				if ( object instanceof Float ) {
					try {
						return Float.valueOf(str).equals(object);
					} catch (Exception e) {
						return false;
					}
				}
				
				if ( object instanceof Short ) {
					try {
						return Short.valueOf(str).equals(object);
					} catch (Exception e) {
						return false;
					}
				}
				
				if ( object instanceof Double ) {
					try {
						return Double.valueOf(str).equals(object);
					} catch (Exception e) {
						return false;
					}
				}
				
				if ( object instanceof Byte ) {
					try {
						return Byte.valueOf(str).equals(object);
					} catch (Exception e) {
						return false;
					}
				}
				
				if ( object instanceof BigInteger ) {
					try {
						return Long.valueOf(str).longValue() == ((BigInteger)object).longValue();
					} catch (Exception e) {
						return false;
					}
				}
				
				if ( object instanceof BigDecimal ) {
					try {
						return Double.valueOf(str).doubleValue() == ((BigDecimal)object).doubleValue();
					} catch (Exception e) {
						return false;
					}
				}
				
				//TODO suppost more type
				
				return str.equals(object);
			} else {
				return object.equals(strObj);
			}
		}
	}
}
