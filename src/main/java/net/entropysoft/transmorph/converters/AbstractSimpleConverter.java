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

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Abstract simple converter 
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 * @param <S> The source
 * @param <D> The destination
 */
public abstract class AbstractSimpleConverter<S,D> implements IConverter {
	private Class sourceClass;
	private Class destinationClass;
	
	public AbstractSimpleConverter(Class sourceClass, Class destinationClass) {
		this.sourceClass = sourceClass;
		this.destinationClass = destinationClass;
	}
	
	public Class getSourceClass() {
		return sourceClass;
	}

	public Class getDestinationClass() {
		return destinationClass;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isType(destinationClass);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceClass.isInstance(sourceObject);
	}

	public Object convert(IConverter elementConverter, Object sourceObject,
			Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException("Cannot convert null to a primitive type");
			}
		}
		return doConvert(elementConverter, (S)sourceObject, destinationType);
	}

	public abstract D doConvert(IConverter elementConverter, S sourceObject,
			Type destinationType) throws ConverterException; 
	
}
