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

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter used when source object is an array and destination type is
 * Collection
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ArrayToCollection extends AbstractContainerConverter {

	private Class<? extends Set> defaultSetClass = HashSet.class;
	private Class<? extends List> defaultListClass = ArrayList.class;

	public ArrayToCollection() {
		this.useObjectPool = true;
	}

	public Class<? extends Set> getDefaultSetClass() {
		return defaultSetClass;
	}

	public void setDefaultSetClass(Class<? extends Set> defaultSetClass) {
		this.defaultSetClass = defaultSetClass;
	}

	public Class<? extends List> getDefaultListClass() {
		return defaultListClass;
	}

	public void setDefaultListClass(Class<? extends List> defaultListClass) {
		this.defaultListClass = defaultListClass;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		Collection<Object> destinationList;
		try {
			destinationList = createDestinationCollection(sourceObject,
					destinationType);
		} catch (Exception e) {
			throw new ConverterException(
					"Could not create destination collection", e);
		}
		if (destinationList == null) {
			throw new ConverterException(
					"Could not create destination collection");
		}
		if (useObjectPool) {
			// add now to object pool so that elements of list can reference it
			context.getConvertedObjectPool().add(this, sourceObject,
					destinationType, destinationList);
		}

		TypeReference<?>[] destinationTypeArguments = destinationType
				.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new TypeReference[] { TypeReference
					.get(Object.class) };
		}

		Object array = sourceObject;
		int arrayLength = Array.getLength(array);
		for (int i = 0; i < arrayLength; i++) {
			Object obj = Array.get(array, i);
			Object convertedObj = elementConverter.convert(context, obj,
					destinationTypeArguments[0]);
			destinationList.add(convertedObj);
		}
		return destinationList;

	}

	private Collection<Object> createDestinationCollection(Object sourceObject,
			TypeReference<?> destinationType) throws InstantiationException,
			IllegalAccessException, ConverterException {
		Class<? extends Collection> clazz = getConcreteCollectionDestinationClass(
				sourceObject, destinationType);
		if (clazz == null) {
			throw new ConverterException(MessageFormat
					.format(
							"Cannot find a concrete class for destination collection ''{0}''",
							destinationType.toHumanString()));
		}
		return clazz.newInstance();
	}

	protected Class<? extends Collection> getConcreteCollectionDestinationClass(
			Object sourceObject, TypeReference<?> destinationType) {
		if (destinationType.hasRawType(Set.class)) {
			return defaultSetClass;
		}
		if (destinationType.hasRawType(List.class)) {
			return defaultListClass;
		}
		if (destinationType.hasRawType(Collection.class)) {
			return defaultListClass;
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
		if (!destinationType.isRawTypeSubOf(Collection.class)) {
			return false;
		}
		return true;
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		Class<?> sourceObjectClass = sourceObject.getClass();
		return sourceObjectClass.isArray();
	}
}