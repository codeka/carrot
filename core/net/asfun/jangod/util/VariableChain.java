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

import static net.asfun.jangod.util.logging.JangodLogger;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import net.asfun.jangod.util.logging.Level;

public class VariableChain {

	static final String GET = "get";
	static final String IS = "is";
	
	private List<String> chain;
	private Object value;

	public VariableChain(List<String> chain, Object value) {
		this.chain = chain;
		this.value = value;
	}

	public Object resolve() {
		for (String name : chain) {
			if (value == null) {
				return null;
			} else {
				value = resolveInternal(name);
			}
		}
		return value;
	}

	@SuppressWarnings("unchecked")
	private Object resolveInternal(String name) {
		// field
		Class<?> clazz = value.getClass();
		try {
			Field field = clazz.getDeclaredField(name);
			return field.get(value);
		} catch (Exception e1) {
			// method
			Method mth1 = null;
			try {
				mth1 = clazz.getDeclaredMethod(name);
			} catch( NoSuchMethodException e) {
				String uname = upperFirst(name);
				try {
					mth1 = clazz.getDeclaredMethod(GET + uname);
//					mth1.setAccessible(array, flag)
				} catch (NoSuchMethodException e2) {
					try {
						mth1 = clazz.getDeclaredMethod(IS + uname);
					} catch (NoSuchMethodException e3) {
						//nothing;
					}
				}
			} catch (SecurityException e) {
				//nothing
			}
			if ( mth1 != null ) {
				try {
					return mth1.invoke(value);
				} catch (Exception e) {
					JangodLogger.log(Level.SEVERE, "resolve variable trigger error.", e.getCause());
				}
			}
//			Method[] mths = clazz.getDeclaredMethods();
//			for (Method mth : mths) {
//				if (mth.getParameterTypes().length != 0
//						|| !Modifier.isPublic(mth.getModifiers())) {
//					continue;
//				}
//				if (name.equalsIgnoreCase(mth.getName())
//						|| ("get" + name).equalsIgnoreCase(mth.getName())
//						|| ("is" + name).equalsIgnoreCase(mth.getName())) {
//					try {
//						return mth.invoke(value);
//					} catch (InvocationTargetException e) {
//						JangodLogger.log(Level.SEVERE, "resolve variable trigger error.", e.getCause());
//					} catch (Exception e) {
//						continue;
//					}
//				}
//			}
		}

		// map
		if (value instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) value;
			if (map.containsKey(name)) {
				return map.get(name);
			}
		}

		// array
		if (value.getClass().isArray()) {
			try {
				return Array.get(value, Integer.parseInt(name));
			} catch (Exception e) {
				// nothing;
			}
		}
		// list
		if (value instanceof List) {
			try {
				return ((List<?>) value).get(Integer.parseInt(name));
			} catch (Exception e) {
				// nothing;
			}
		}

		return null;
	}
	
	private String upperFirst(String name) {
		char c = name.charAt(0);
		if ( Character.isLowerCase(c) ) {
			return String.valueOf(c).toUpperCase().concat(name.substring(1));
		} else {
			return name;
		}
	}

}
