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
package net.entropysoft.transmorph;

import java.lang.reflect.Type;
import java.text.MessageFormat;

import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Convert an object to another object.
 * 
 * <p>
 * This class is not thread-safe
 * </p>
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class Transmorph {

	private final MultiConverter multiConverter;

	/**
	 * Creates Transmorph object
	 * 
	 * @param classLoader
	 *            classLoader to use when loading classes by name
	 * @param converters
	 *            the converter that will be used.
	 */
	public Transmorph(IConverter converter) {
		// this constructor is not really needed but when converted to JDK 1.4,
		// it is useful
		this(new IConverter[] { converter });
	}

	/**
	 * Creates Transmorph Object.
	 * 
	 * @param typeFactory
	 *            factory that creates types from their signatures
	 * @param converters
	 *            the converters that will be used. The order is important as
	 *            they will be tried one by one in order
	 */
	public Transmorph(IConverter... converters) {
		this.multiConverter = new MultiConverter(false, converters);
		this.multiConverter.setElementConverter(multiConverter);
	}

	/**
	 * Convert an object to another object with given type
	 * 
	 * @param <T>
	 * @param source
	 *            object to convert
	 * @param typeReference
	 *            reference to {@link java.lang.reflect.Type}
	 * @return the converted object if conversion failed
	 * @throws ConverterException
	 */
	public <T> T convert(Object source, TypeReference<T> typeReference)
			throws ConverterException {
		return (T) convert(new ConversionContext(), source, typeReference);
	}

	public <T> T convert(Object source, Class<T> clazz, Class<?>[] typeArgs)
			throws ConverterException {
		return clazz.cast(convert(new ConversionContext(), source,
				TypeReference.get(clazz, typeArgs)));
	}

	public Object convert(Object source, Type type) throws ConverterException {
		return convert(new ConversionContext(), source, TypeReference.get(type));
	}

	/**
	 * Convert an object to another object with given class
	 * 
	 * @param <T>
	 * @param source
	 *            object to convert
	 * @param clazz
	 *            destination class
	 * @return the converted object
	 * @throws ConverterException
	 *             if conversion failed
	 */
	public <T> T convert(Object source, Class<T> clazz)
			throws ConverterException {
		return (T) convert(new ConversionContext(), source, clazz);
	}

	/**
	 * Convert an object to another object with given class
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
	public <T> T convert(ConversionContext context, Object source,
			Class<T> clazz) throws ConverterException {
		return (T) convert(context, source, TypeReference.get(clazz));
	}

	public Object convert(ConversionContext context, Object source, Type type)
			throws ConverterException {
		return convert(context, source, TypeReference.get(type));
	}

	/**
	 * Convert an object to another object given a parameterized type signature
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
	public <T> T convert(ConversionContext context, Object source,
			TypeReference<T> destinationType) throws ConverterException {
		try {
			return (T) multiConverter.convert(context, source, destinationType);
		} catch (ConverterException e) {
			throw e;
		} catch (Exception e) {
			// There is a problem with one converter. This should not happen.
			// Either there is a bug in this converter or it is not properly
			// configured
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									source == null ? "null" : source.getClass()
											.getName(), destinationType), e);
		}
	}

	public boolean canHandle(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) {
		return multiConverter.canHandle(context, sourceObject, destinationType);
	}

}
