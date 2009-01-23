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
package net.entropysoft.transmorph.converters.beans.utils;

import java.lang.reflect.Method;

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
	public static Method getMethod(Class clazz, String name,
			Class<?>... parameterTypes) {
		try {
			return clazz.getMethod(name, parameterTypes);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

}