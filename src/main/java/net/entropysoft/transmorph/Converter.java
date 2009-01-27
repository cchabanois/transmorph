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
	private boolean useInternalFormFullyQualifiedName;

	/**
	 * Creates a Converter object
	 * 
	 * @param classLoader
	 *            classLoader to use when loading classes by name
	 * @param converters
	 *            the converters that will be used. The order is important as
	 *            they will be tried one by one in order
	 */
	public Converter(ClassLoader classLoader, IConverter[] converters) {
		this(new TypeFactory(new ClassFactory(classLoader)), converters);
	}

	/**
	 * Creates a Converter Object.
	 * 
	 * <p>
	 * The classLoader used when loading classes by name will be the context
	 * class loader
	 * </p>
	 * 
	 * @param converters
	 *            the converters that will be used. The order is important as
	 *            they will be tried one by one in order
	 */
	public Converter(IConverter[] converters) {
		this(Thread.currentThread().getContextClassLoader(), converters);
	}

	/**
	 * Creates a Converter Object.
	 * 
	 * @param typeFactory
	 *            factory that creates types from their signatures
	 * @param converters
	 *            the converters that will be used. The order is important as
	 *            they will be tried one by one in order
	 */
	public Converter(TypeFactory typeFactory, IConverter[] converters) {
		this.typeFactory = typeFactory;

		// use this converter as element converter for all container converters
		// that have no element converter set
		for (IConverter converter : converters) {
			if (converter instanceof IContainerConverter) {
				IContainerConverter containerConverter = (IContainerConverter) converter;
				if (containerConverter.getElementConverter() == null) {
					containerConverter.setElementConverter(this);
				}
			}
		}
		this.converters.addAll(Arrays.asList(converters));
	}

	public boolean isUseInternalFormFullyQualifiedName() {
		return useInternalFormFullyQualifiedName;
	}

	/**
	 * If you use internal form fully qualified names (which is the case by
	 * default), fqn in parameterizedTypeSignature must be in the form
	 * 'java/lang/Thread'. Otherwise fqn must be of the form 'java.lang.Thread'
	 * 
	 * @param useInternalFormFullyQualifiedName
	 * @see {@link #convert(Object, String)}
	 */
	public void setUseInternalFormFullyQualifiedName(
			boolean useInternalFormFullyQualifiedName) {
		this.useInternalFormFullyQualifiedName = useInternalFormFullyQualifiedName;
	}

	/**
	 * Convert an object to another object with given class and given type
	 * arguments (if any)
	 * 
	 * @param source
	 *            object to convert
	 * @param clazz
	 *            destination class
	 * @param typeArgs
	 *            the type arguments or an empty array if none
	 * @return the converted object
	 * @throws ConverterException
	 *             if conversion failed
	 */
	public Object convert(Object source, Class clazz, Class[] typeArgs)
			throws ConverterException {
		return convert(new ConversionContext(), source, clazz, typeArgs);
	}

	/**
	 * Convert an object to another object with given class and given type
	 * arguments (if any)
	 * 
	 * @param context
	 * @param source
	 *            object to convert
	 * @param clazz
	 *            destination class
	 * @param typeArgs
	 *            the type arguments or an empty array if none
	 * @return the converted object
	 * @throws ConverterException
	 *             if conversion failed
	 */
	public Object convert(ConversionContext context, Object source, Class clazz,
			Class[] typeArgs) throws ConverterException {
		Type destinationType = typeFactory.getType(clazz, typeArgs);

		return convert(context, source, destinationType);
	}

	/**
	 * Convert an object to another object with given class
	 * 
	 * @param source
	 *            object to convert
	 * @param clazz
	 *            destination class
	 * @return the converted object
	 * @throws ConverterException
	 *             if conversion failed
	 */
	public Object convert(Object source, Class clazz) throws ConverterException {
		return convert(new ConversionContext(), source, clazz);
	}

	/**
	 * Convert an object to another object with given class
	 * 
	 * @param context
	 * @param source
	 *            object to convert
	 * @param clazz
	 *            destination class
	 * @return the converted object
	 * @throws ConverterException
	 *             if conversion failed
	 */
	public Object convert(ConversionContext context, Object source, Class clazz)
			throws ConverterException {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature(clazz);
		Type destinationType = typeFactory.getType(typeSignature);

		return convert(context, source, destinationType);
	}

	/**
	 * Convert an object to another object given a parameterized type signature
	 * 
	 * @param source
	 * @param parameterizedTypeSignature
	 * @return
	 * @throws ConverterException
	 * @see {@link #setUseInternalFormFullyQualifiedName(boolean)}
	 */
	public Object convert(Object source, String parameterizedTypeSignature)
			throws ConverterException {
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(
				parameterizedTypeSignature, useInternalFormFullyQualifiedName);
		Type destinationType = typeFactory.getType(typeSignature);

		return convert(new ConversionContext(), source, destinationType);
	}

	/**
	 * Convert an object to another object given a parameterized type signature
	 * @param context
	 * @param source
	 * @param parameterizedTypeSignature
	 * @return
	 * @throws ConverterException
	 * @see {@link #setUseInternalFormFullyQualifiedName(boolean)} 
	 */
	public Object convert(ConversionContext context, Object source, String parameterizedTypeSignature)
			throws ConverterException {
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(
				parameterizedTypeSignature, useInternalFormFullyQualifiedName);
		Type destinationType = typeFactory.getType(typeSignature);

		return convert(context, source, destinationType);
	}

	/**
	 * Convert an object to another object given a parameterized type signature
	 * 
	 * @param destinationType
	 *            the destination type
	 * @param source
	 *            the source object
	 * 
	 * @return the converted object
	 * @throws ConverterException
	 */
	public Object convert(ConversionContext context, Object source,
			Type destinationType) throws ConverterException {
		ConverterException firstException = null;

		try {
			for (IConverter converter : converters) {
				if (converter.canHandle(context, source, destinationType)) {
					try {
						return converter.convert(context, source,
								destinationType);
					} catch (ConverterException e) {
						// canHandle do
						// not verify all cases. An other converter
						// might successfully convert the source to destination
						// type
						if (firstException == null)
							firstException = e;
					}
				}
			}
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

	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		for (IConverter converter : converters) {
			if (!canHandle(context, sourceObject, destinationType)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * get the type factory that can be used to create types from their
	 * signatures
	 * 
	 * @return
	 */
	public TypeFactory getTypeFactory() {
		return typeFactory;
	}

}
