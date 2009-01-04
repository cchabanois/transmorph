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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.entropysoft.transmorph.signature.ClassFactory;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeFactory;

/**
 * Convert an object to another object
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class Converter implements IConverter {

	private List<IConverter> converters = new ArrayList<IConverter>();
	private TypeFactory typeFactory;

	private Converter(ClassLoader classLoader, IConverter[] converters) {
		this(new TypeFactory(new ClassFactory(classLoader)), converters);
	}

	private Converter(TypeFactory typeFactory, IConverter[] converters) {
		this.typeFactory = typeFactory;
		this.converters.addAll(Arrays.asList(converters));
	}

	public static Converter getConverter(TypeFactory typeFactory, IConverter[] converters) {
		return new Converter(typeFactory, converters);
	}
	
	public static Converter getConverter(ClassLoader classLoader,
			IConverter[] converters) {
		return new Converter(classLoader, converters);
	}

	public static Converter getConverter(IConverter[] converters) {
		return getConverter(Thread.currentThread().getContextClassLoader(),
				converters);
	}

	public Object convert(Object source, Class clazz, Class[] typeArgs)
			throws ConverterException {
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(
				clazz, typeArgs);
		Type destinationType = typeFactory.getType(typeSignature);

		return convert(this, source, destinationType);
	}

	public Object convert(Object source, Class clazz) throws ConverterException {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature(clazz);
		Type destinationType = typeFactory.getType(typeSignature);

		return convert(this, source, destinationType);
	}

	/**
	 * Convert an object to another object given a parameterized type signature
	 * 
	 * @param source
	 * @param parameterizedTypeSignature
	 * @return
	 * @throws ConverterException
	 */
	public Object convert(Object source, String parameterizedTypeSignature)
			throws ConverterException {
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(parameterizedTypeSignature);
		Type destinationType = typeFactory.getType(typeSignature);

		return convert(this, source, destinationType);
	}

	/**
	 * Convert an object to another object given a parameterized type signature
	 * 
	 * @param elementConverter
	 *            the converter to use for elements in source object (if any)
	 * @param source
	 *            the source object
	 * @param destinationType
	 *            the destination type
	 * @return the converted object
	 * @throws ConverterException
	 */
	public Object convert(IConverter elementConverter, Object source,
			Type destinationType) throws ConverterException {
		ConverterException firstException = null;

		for (IConverter converter : converters) {
			if (converter.canHandleDestinationType(destinationType)
					&& converter.canHandleSourceObject(source)) {
				try {

					return converter.convert(this, source, destinationType);
				} catch (ConverterException e) {
					// isHandled does not verify all cases. An other converter
					// might successfully convert the source to destination type
					if (firstException == null)
						firstException = e;
				}
			}
		}

		if (firstException != null) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									source == null ? "null" : source.getClass()
											.getName(), destinationType),
					firstException);
		} else {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not convert given object with class ''{0}'' to object with type signature ''{1}''",
									source == null ? "null" : source.getClass()
											.getName(), destinationType));
		}
	}

	public List<IConverter> getConverters() {
		return converters;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		for (IConverter converter : converters) {
			if (!canHandleDestinationType(destinationType)) {
				return false;
			}
		}
		return true;
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		for (IConverter converter : converters) {
			if (!canHandleSourceObject(sourceObject)) {
				return false;
			}
		}
		return true;
	}

}
