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

import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.signature.ClassFactory;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeFactory;

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
public class Transmorph implements IConverter {

	private MultiConverter multiConverter;
	private TypeFactory typeFactory;
	private boolean useInternalFormFullyQualifiedName = true;

	/**
	 * Creates a Converter object
	 * 
	 * @param classLoader
	 *            classLoader to use when loading classes by name
	 * @param converters
	 *            the converter that will be used.
	 */
	public Transmorph(ClassLoader classLoader, IConverter converter) {
		// this method is not really needed but when converted to JDK 1.4, it is
		// useful
		this(new TypeFactory(new ClassFactory(classLoader)),
				new IConverter[] { converter });
	}

	/**
	 * Creates a Converter object
	 * 
	 * @param classLoader
	 *            classLoader to use when loading classes by name
	 * @param converters
	 *            the converters that will be used. The order is important as
	 *            they will be tried one by one in order
	 */
	public Transmorph(ClassLoader classLoader, IConverter... converters) {
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
	public Transmorph(IConverter... converters) {
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
	public Transmorph(TypeFactory typeFactory, IConverter... converters) {
		this.typeFactory = typeFactory;
		this.multiConverter = new MultiConverter(false, converters);
		this.multiConverter.setElementConverter(multiConverter);
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
	public Object convert(ConversionContext context, Object source,
			Class clazz, Class[] typeArgs) throws ConverterException {
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
	 * Convert an object to another object with given type
	 * 
	 * @param source
	 * @param destinationType
	 * @return
	 * @throws ConverterException
	 */
	public Object convert(Object source, Type destinationType)
			throws ConverterException {
		return convert(new ConversionContext(), source, destinationType);
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
	 * 
	 * @param context
	 * @param source
	 * @param parameterizedTypeSignature
	 * @return
	 * @throws ConverterException
	 * @see {@link #setUseInternalFormFullyQualifiedName(boolean)}
	 */
	public Object convert(ConversionContext context, Object source,
			String parameterizedTypeSignature) throws ConverterException {
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
		try {
			return multiConverter.convert(context, source, destinationType);
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
			Type destinationType) {
		return multiConverter.canHandle(context, sourceObject, destinationType);
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
