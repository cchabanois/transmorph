/*
 * Copyright 2008-2010 the original author or authors.
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
package net.entropysoft.transmorph.converters.beans;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import net.entropysoft.transmorph.type.TypeReference;

/**
 * Default implementation for {@link IMapToBeanMapping}
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class DefaultMapToBeanMapping implements IMapToBeanMapping {

	public String getPropertyName(Map<String, Object> map, String key, Object bean,
			Map<String, Method> setterMethods) {
		if ("class".equals(key)) {
			return null;
		}
		return key;
	}

	public TypeReference<?> getConcreteDestinationType(Map<String, Object> map,
			 TypeReference<?> destinationType) {
		Class<?> rawType = destinationType.getRawType();
		if (rawType.isInterface()
				|| Modifier.isAbstract(rawType.getModifiers())) {
			return null;
		}
		return destinationType;
	}

}
