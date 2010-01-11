/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.entropysoft.transmorph.utils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Some utility methods
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class BeanUtils {

	/**
	 * Return a capitalized version of the specified property name.
	 * 
	 * @param s
	 *            The property name
	 */
	public static String capitalizePropertyName(String s) {
		if (s.length() == 0) {
			return s;
		}

		char[] chars = s.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	/**
	 * Get method with given name and parameter types or return null if it does
	 * not exist
	 * 
	 * @param clazz
	 * @param name
	 * @param parameterTypes
	 * @return
	 */
	public static Method getMethod(Class<?> clazz, String name,
			Class<?>... parameterTypes) {
		try {
			return clazz.getMethod(name, parameterTypes);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * get a map of public setters (propertyName -> Method)
	 * 
	 * @param clazz
	 * @return
	 */
	public static Map<String, Method> getSetters(Class<?> clazz) {
		Map<String, Method> setters = new HashMap<String, Method>();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (method.getParameterTypes().length == 1
					&& methodName.startsWith("set") && methodName.length() > 3
					&& method.getReturnType() == Void.TYPE) {
				String propertyName = methodName.substring(3, 4).toLowerCase();
				if (methodName.length() > 4) {
					propertyName += methodName.substring(4);
				}
				setters.put(propertyName, method);
			}
		}
		return setters;
	}

	/**
	 * get a map of public getters (propertyName -> Method)
	 * 
	 * @param clazz
	 * @return
	 */
	public static Map<String, Method> getGetters(Class<?> clazz) {
		Map<String, Method> getters = new HashMap<String, Method>();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (method.getParameterTypes().length == 0) {
				if (methodName.startsWith("get") && methodName.length() > 3) {
					String propertyName = methodName.substring(3, 4)
							.toLowerCase();
					if (methodName.length() > 4) {
						propertyName += methodName.substring(4);
					}
					getters.put(propertyName, method);
				} else if (methodName.startsWith("is")
						&& methodName.length() > 2
						&& Boolean.TYPE.equals(method.getReturnType())) {
					String propertyName = methodName.substring(2, 3)
							.toLowerCase();
					if (methodName.length() > 3) {
						propertyName += methodName.substring(3);
					}
					getters.put(propertyName, method);
				}
			}
		}
		return getters;
	}

	/**
	 * get the getter method corresponding to given property
	 * 
	 */
	public static Method getGetterPropertyMethod(Class<?> type,
			String propertyName) {
		String sourceMethodName = "get"
				+ BeanUtils.capitalizePropertyName(propertyName);

		Method sourceMethod = BeanUtils.getMethod(type, sourceMethodName);

		if (sourceMethod == null) {
			sourceMethodName = "is"
					+ BeanUtils.capitalizePropertyName(propertyName);
			sourceMethod = BeanUtils.getMethod(type, sourceMethodName);
			if (sourceMethod != null
					&& sourceMethod.getReturnType() != Boolean.TYPE) {
				sourceMethod = null;
			}
		}
		return sourceMethod;
	}

	/**
	 * get the setter method corresponding to given property
	 * 
	 */
	public static Method getSetterPropertyMethod(Class<?> type,
			String propertyName) {
		String sourceMethodName = "set"
				+ BeanUtils.capitalizePropertyName(propertyName);

		Method sourceMethod = BeanUtils.getMethod(type, sourceMethodName);

		return sourceMethod;
	}

}
