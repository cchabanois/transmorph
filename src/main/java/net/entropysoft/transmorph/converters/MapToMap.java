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
package net.entropysoft.transmorph.converters;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.ClassType;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source objet type and destination type are maps
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MapToMap extends AbstractContainerConverter {

	private Class<? extends Map> defaultMapClass = HashMap.class;

	public Object convert(Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		ClassType mapDestinationType = (ClassType) destinationType;
		Map<Object, Object> sourceMap = (Map<Object, Object>) sourceObject;
		Map<Object, Object> destinationMap;
		try {
			destinationMap = createDestinationMap(sourceMap, mapDestinationType);
		} catch (Exception e) {
			throw new ConverterException("Could not create destination map", e);
		}
		if (destinationMap == null) {
			throw new ConverterException("Could not create destination map");
		}

		Type[] destinationTypeArguments;
		try {
			destinationTypeArguments = getDestinationTypeArguments(mapDestinationType);
		} catch (ClassNotFoundException e) {
			throw new ConverterException(
					"Could not get destination type arguments", e);
		}

		for (Iterator<Map.Entry<Object, Object>> it = sourceMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<Object, Object> mapEntry = it.next();
			Object key = elementConverter.convert(mapEntry
					.getKey(), destinationTypeArguments[0]);
			Object value = elementConverter.convert(mapEntry
					.getValue(), destinationTypeArguments[1]);
			destinationMap.put(key, value);
		}

		return destinationMap;
	}

	protected Type[] getDestinationTypeArguments(ClassType mapDestinationType)
			throws ClassNotFoundException {
		if (mapDestinationType.isSubOf(Properties.class)) {
			// Properties extends Hashtable<Object,Object> but should contain
			// only strings
			return new Type[] {
					mapDestinationType.getTypeFactory().getStringType(),
					mapDestinationType.getTypeFactory().getStringType() };
		}
		Type[] destinationTypeArguments = mapDestinationType.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new Type[] {
					mapDestinationType.getTypeFactory().getObjectType(),
					mapDestinationType.getTypeFactory().getObjectType() };
		}
		return destinationTypeArguments;
	}

	private Map<Object, Object> createDestinationMap(Map sourceObject,
			Type destinationType) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class<? extends Map> clazz = getConcreteMapDestinationClass(
				sourceObject, destinationType);
		if (clazz == null) {
			return null;
		}
		return clazz.newInstance();
	}

	protected Class<? extends Map> getConcreteMapDestinationClass(
			Map sourceObject, Type destinationType)
			throws ClassNotFoundException {
		if (destinationType.isType(Map.class)) {
			return defaultMapClass;
		}
		Class destinationClass = destinationType.getType();
		if (destinationClass.isInterface()
				|| Modifier.isAbstract(destinationClass.getModifiers())) {
			return null;
		}
		try {
			destinationClass.getConstructor(new Class[0]);
		} catch (Exception e) {
			return null;
		}
		return destinationClass;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isSubOf(Map.class);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}

		return sourceObject instanceof Map;
	}

}
