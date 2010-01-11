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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.TypeReference;
import net.entropysoft.transmorph.utils.BeanUtils;

/**
 * Converter used to convert a Bean to a map.
 * 
 * Map keys will be the property names
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class BeanToMap extends AbstractContainerConverter {
	private Class<? extends Map> defaultMapClass = HashMap.class;

	public BeanToMap() {
		this.useObjectPool = true;
	}

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		if (!destinationType.isRawTypeSubOf(Map.class)) {
			return false;
		}

		TypeReference<?>[] destinationTypeArguments = destinationType
				.getTypeArguments();
		if (destinationTypeArguments.length > 0) {
			// must be Map<String, ...> or Map<Object, ...>
			if (!destinationTypeArguments[0].isType(String.class)
					&& destinationTypeArguments[0].isType(Object.class)) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		try {
			sourceObject.getClass().getConstructor(new Class[0]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Map<Object, Object> destinationMap;
		try {
			destinationMap = createDestinationMap(destinationType);
		} catch (Exception e) {
			throw new ConverterException("Could not create destination map", e);
		}
		if (destinationMap == null) {
			throw new ConverterException("Could not create destination map");
		}

		if (useObjectPool) {
			context.getConvertedObjectPool().add(this, sourceObject,
					destinationType, destinationMap);
		}

		TypeReference<?>[] destinationTypeArguments = getDestinationTypeArguments(destinationType);
		Map<String, Method> getterMethods = BeanUtils
				.getGetters(destinationType.getRawType());

		for (Map.Entry<String, Method> entry : getterMethods.entrySet()) {
			String propertyName = entry.getKey();
			Object value = elementConverter.convert(context, entry.getValue(),
					destinationTypeArguments[1]);

			destinationMap.put(propertyName, value);
		}

		return null;
	}

	private Map<Object, Object> createDestinationMap(
			TypeReference<?> destinationType) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class<? extends Map> clazz = getConcreteMapDestinationClass(destinationType);
		if (clazz == null) {
			return null;
		}
		return clazz.newInstance();
	}

	protected Class<? extends Map> getConcreteMapDestinationClass(
			TypeReference<?> destinationType) throws ClassNotFoundException {
		if (destinationType.hasRawType(Map.class)) {
			return defaultMapClass;
		}
		Class destinationClass = destinationType.getRawType();
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

	protected TypeReference<?>[] getDestinationTypeArguments(
			TypeReference<?> mapDestinationType) {
		if (mapDestinationType.isRawTypeSubOf(Properties.class)) {
			// Properties extends Hashtable<Object,Object> but should contain
			// only strings
			return new TypeReference<?>[] { TypeReference.get(String.class),
					TypeReference.get(String.class) };
		}
		TypeReference<?>[] destinationTypeArguments = mapDestinationType
				.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new TypeReference[] {
					TypeReference.get(Object.class),
					TypeReference.get(Object.class) };
		}
		return destinationTypeArguments;
	}

}
