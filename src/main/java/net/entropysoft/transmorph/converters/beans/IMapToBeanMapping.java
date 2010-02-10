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
import java.util.Map;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Defines rules for Map to Bean mapping
 * 
 * @author cedric
 * 
 */
public interface IMapToBeanMapping {

	/**
	 * get the property name corresponding to given map key
	 * 
	 * @return the property name or null to ignore this map key
	 */
	public String getPropertyName(Map<String, Object> map, String key,
			Object bean, Map<String, Method> setterMethods)
			throws ConverterException;

	/**
	 * get the concrete destination type.
	 * 
	 * User may ask to convert a Map to an interface or an abstract class. This
	 * method will return the concrete destination type
	 * 
	 * @param map
	 * @param destinationType
	 * @return the concrete destination type
	 */
	public TypeReference<?> getConcreteDestinationType(Map<String, Object> map,
			TypeReference<?> destinationType) throws ConverterException;
}
