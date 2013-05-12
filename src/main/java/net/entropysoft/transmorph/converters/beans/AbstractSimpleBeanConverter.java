/*
 * Copyright 2008-2013 the original author or authors.
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

import java.lang.reflect.Type;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractSimpleContainerConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Base class to create a custom converter for a bean. It is often easier to
 * create a custom converter based on this class than using {@link BeanToBean}.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 * @param <S>
 * @param <D>
 */
public abstract class AbstractSimpleBeanConverter<S, D> extends
		AbstractSimpleContainerConverter<S, D> {

	protected AbstractSimpleBeanConverter(Class<S> sourceClass,
			Class<D> destinationClass) {
		super(sourceClass, destinationClass);
	}

	/**
	 * Convert element to another object with given class
	 * 
	 * @param <T>
	 * @param context
	 * @param source
	 *            object to convert
	 * @param clazz
	 *            destination class
	 * @return the converted object
	 * @throws ConverterException
	 *             if conversion failed
	 */
	public <T> T convertElement(ConversionContext context, Object source,
			Class<T> clazz) throws ConverterException {
		return (T) convertElement(context, source, TypeReference.get(clazz));
	}

	public Object convertElement(ConversionContext context, Object source,
			Type type) throws ConverterException {
		return convertElement(context, source, TypeReference.get(type));
	}

	/**
	 * Convert element to another object given a parameterized type signature
	 * 
	 * @param context
	 * @param destinationType
	 *            the destination type
	 * @param source
	 *            the source object
	 * 
	 * @return the converted object
	 * @throws ConverterException
	 *             if conversion failed
	 */
	@SuppressWarnings("unchecked")
	public <T> T convertElement(ConversionContext context, Object source,
			TypeReference<T> destinationType) throws ConverterException {
		return (T) elementConverter.convert(context, source, destinationType);
	}

}
