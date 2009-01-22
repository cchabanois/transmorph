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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.ClassType;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source object type is collection and destination type is
 * also collection
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class CollectionToCollection extends AbstractContainerConverter {

	private Class<? extends Set> defaultSetClass = HashSet.class;
	private Class<? extends List> defaultListClass = ArrayList.class;

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

	public Object convert(Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		ClassType collectionClassType = (ClassType) destinationType;
		Collection<Object> sourceCollection = (Collection<Object>) sourceObject;
		Collection<Object> destinationCollection;
		try {
			destinationCollection = createDestinationCollection(
					sourceCollection, destinationType);
		} catch (Exception e) {
			throw new ConverterException(
					"Could not create destination collection", e);
		}
		if (destinationCollection == null) {
			throw new ConverterException(
					"Could not create destination collection");
		}
		
		Type[] destinationTypeArguments = collectionClassType
				.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new Type[] { destinationType
					.getTypeFactory().getObjectType() };
		}

		for (Object obj : sourceCollection) {
			Object convertedObj = elementConverter.convert(obj,
					destinationTypeArguments[0]);
			destinationCollection.add(convertedObj);
		}
		return destinationCollection;

	}

	private Collection<Object> createDestinationCollection(
			Collection sourceObject, Type destinationType)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		Class<? extends Collection> clazz = getConcreteCollectionDestinationClass(
				sourceObject, destinationType);
		return clazz.newInstance();
	}

	protected Class<? extends Collection> getConcreteCollectionDestinationClass(
			Collection sourceObject, Type destinationType)
			throws ClassNotFoundException {
		if (destinationType.isType(Set.class)) {
			return defaultSetClass;
		}
		if (destinationType.isType(List.class)) {
			return defaultListClass;
		}
		if (destinationType.isType(Collection.class)) {
			if (sourceObject instanceof Set) {
				return defaultSetClass;
			} else {
				return defaultListClass;
			}
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
			return destinationType.isSubOf(Collection.class);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof Collection;
	}

}
