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
package net.entropysoft.transmorph.converters.collections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.ClassType;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter used when source objet type and destination type are maps
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MapToMap extends AbstractContainerConverter {

	private Class<? extends Map> defaultMapClass = HashMap.class;
	private IConverter keyConverter;
	private IConverter valueConverter;

	public MapToMap() {
		this.useObjectPool = true;
	}

	public IConverter getKeyConverter() {
		return keyConverter;
	}

	public void setKeyConverter(IConverter keyConverter) {
		this.keyConverter = keyConverter;
	}

	public IConverter getValueConverter() {
		return valueConverter;
	}

	public void setValueConverter(IConverter valueConverter) {
		this.valueConverter = valueConverter;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		Map<Object, Object> sourceMap = (Map<Object, Object>) sourceObject;
		Map<Object, Object> destinationMap;
		try {
			destinationMap = createDestinationMap(sourceMap, destinationType);
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

		for (Iterator<Map.Entry<Object, Object>> it = sourceMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<Object, Object> mapEntry = it.next();
			IConverter converter = keyConverter;
			if (converter == null) {
				converter = elementConverter;
			}
			Object key = converter.convert(context, mapEntry.getKey(),
					destinationTypeArguments[0]);

			converter = valueConverter;
			if (converter == null) {
				converter = elementConverter;
			}

			Object value = converter.convert(context, mapEntry.getValue(),
					destinationTypeArguments[1]);
			destinationMap.put(key, value);
		}

		return destinationMap;
	}

	protected TypeReference<?>[] getDestinationTypeArguments(
			TypeReference mapDestinationType) {
		if (mapDestinationType.isSubOf(Properties.class)) {
			// Properties extends Hashtable<Object,Object> but should contain
			// only strings
			return new TypeReference<?>[] { TypeReference.get(String.class),
					TypeReference.get(String.class) };
		}
		TypeReference[] destinationTypeArguments = mapDestinationType
				.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new TypeReference[] {
					TypeReference.get(Object.class),
					TypeReference.get(Object.class) };
		}
		return destinationTypeArguments;
	}

	private Map<Object, Object> createDestinationMap(Map sourceObject,
			TypeReference<?> destinationType) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class<? extends Map> clazz = getConcreteMapDestinationClass(
				sourceObject, destinationType);
		if (clazz == null) {
			return null;
		}
		return clazz.newInstance();
	}

	protected Class<? extends Map> getConcreteMapDestinationClass(
			Map sourceObject, TypeReference<?> destinationType)
			throws ClassNotFoundException {
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

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isSubOf(Map.class);
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}

		return sourceObject instanceof Map;
	}

}
