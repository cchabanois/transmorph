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
package net.entropysoft.transmorph.converters.beans;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.beans.utils.BeanUtils;
import net.entropysoft.transmorph.converters.beans.utils.ClassPair;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used to convert a Bean to another bean.
 * 
 * By default, it will only work if destination bean class is the same as source
 * bean class. You must add {@link BeanToBeanMapping}s for converting between
 * beans with different types.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class BeanToBean extends AbstractContainerConverter {
	private IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider;
	private Map<ClassPair, BeanToBeanMapping> beanToBeanMappings = new HashMap<ClassPair, BeanToBeanMapping>();
	private boolean handleTargetClassSameAsSourceClass = true;

	public BeanToBean() {
		this.useObjectPool = true;
	}

	public IBeanPropertyTypeProvider getBeanDestinationPropertyTypeProvider() {
		return beanDestinationPropertyTypeProvider;
	}

	/**
	 * set the provider for destination bean property types
	 * 
	 * @param beanDestinationPropertyTypeProvider
	 */
	public void setBeanDestinationPropertyTypeProvider(
			IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider) {
		this.beanDestinationPropertyTypeProvider = beanDestinationPropertyTypeProvider;
	}

	public boolean isHandleTargetClassSameAsSourceClass() {
		return handleTargetClassSameAsSourceClass;
	}

	/**
	 * By default, BeanToBean can handle the case where target class is the same
	 * as source class. If you don't want it to handle this case (because you
	 * are using {@link IdentityConverter} for this case for example), you can
	 * set this property to false
	 * 
	 * @param useIfTargetClassSameAsSourceClass
	 */
	public void setHandleTargetClassSameAsSourceClass(
			boolean useIfTargetClassSameAsSourceClass) {
		this.handleTargetClassSameAsSourceClass = useIfTargetClassSameAsSourceClass;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {
		Class destinationClass;
		try {
			destinationClass = destinationType.getType();
		} catch (ClassNotFoundException e) {
			throw new ConverterException(
					"Could not get destination type class", e);
		}

		if (sourceObject == null) {
			return null;
		}

		if (!canHandle(sourceObject.getClass(), destinationClass)) {
			throw new ConverterException("Could not get bean to bean mapping");
		}

		// get destination property => method
		Map<String, Method> destinationSetters;
		destinationSetters = BeanUtils.getSetters(destinationClass);

		// create destination bean
		Object resultBean;
		try {
			resultBean = destinationClass.newInstance();
		} catch (Exception e) {
			throw new ConverterException(MessageFormat.format(
					"Could not create instance of ''{0}''", destinationType
							.getName()), e);
		}
		if (useObjectPool) {
			context.getConvertedObjectPool().add(this, sourceObject,
					destinationType, resultBean);
		}

		for (String destinationPropertyName : destinationSetters.keySet()) {
			Method destinationMethod = destinationSetters
					.get(destinationPropertyName);

			Method sourceMethod = getPropertySourceMethod(sourceObject,
					resultBean, destinationPropertyName);

			if (sourceMethod == null) {
				// no source property corresponding to destination property
				continue;
			}

			Object sourcePropertyValue;
			try {
				sourcePropertyValue = sourceMethod.invoke(sourceObject);
			} catch (Exception e) {
				throw new ConverterException("Could not get property for bean",
						e);
			}

			java.lang.reflect.Type parameterType = destinationMethod
					.getGenericParameterTypes()[0];
			Type originalType = destinationType.getTypeFactory().getType(
					parameterType);
			Type propertyDestinationType = getBeanPropertyType(resultBean
					.getClass(), destinationPropertyName, originalType);

			Object destinationPropertyValue = elementConverter.convert(context,
					sourcePropertyValue, propertyDestinationType);

			try {
				destinationMethod.invoke(resultBean, destinationPropertyValue);
			} catch (Exception e) {
				throw new ConverterException("Could not set property for bean",
						e);
			}
		}

		return resultBean;
	}

	/**
	 * get the property source method corresponding to given destination
	 * property
	 * 
	 * @param sourceObject
	 * @param destinationObject
	 * @param destinationProperty
	 * @return
	 */
	private Method getPropertySourceMethod(Object sourceObject,
			Object destinationObject, String destinationProperty) {
		BeanToBeanMapping beanToBeanMapping = beanToBeanMappings
				.get(new ClassPair(sourceObject.getClass(), destinationObject
						.getClass()));
		String sourceProperty = null;
		if (beanToBeanMapping != null) {
			sourceProperty = beanToBeanMapping
					.getSourceProperty(destinationProperty);
		}
		if (sourceProperty == null) {
			sourceProperty = destinationProperty;
		}

		String sourceMethodName = "get"
				+ BeanUtils.capitalizePropertyName(sourceProperty);

		Method sourceMethod = BeanUtils.getMethod(sourceObject.getClass(),
				sourceMethodName);

		if (sourceMethod == null) {
			sourceMethodName = "is"
					+ BeanUtils.capitalizePropertyName(sourceProperty);
			sourceMethod = BeanUtils.getMethod(sourceObject.getClass(),
					sourceMethodName);
			if (sourceMethod != null
					&& sourceMethod.getReturnType() != Boolean.TYPE) {
				sourceMethod = null;
			}
		}
		return sourceMethod;
	}

	/**
	 * get the bean property type
	 * 
	 * @param clazz
	 * @param propertyName
	 * @param originalType
	 * @return
	 */
	protected Type getBeanPropertyType(Class clazz, String propertyName,
			Type originalType) {
		Type propertyDestinationType = null;
		if (beanDestinationPropertyTypeProvider != null) {
			propertyDestinationType = beanDestinationPropertyTypeProvider
					.getPropertyType(clazz, propertyName, originalType);
		}
		if (propertyDestinationType == null) {
			propertyDestinationType = originalType;
		}
		return propertyDestinationType;
	}

	/**
	 * Add a mapping of properties between two beans
	 * 
	 * @param beanToBeanMapping
	 */
	public void addBeanToBeanMapping(BeanToBeanMapping beanToBeanMapping) {
		beanToBeanMappings.put(new ClassPair(
				beanToBeanMapping.getSourceClass(), beanToBeanMapping
						.getDestinationClass()), beanToBeanMapping);
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			// make sure that destinationType has a constructor with no
			// parameters
			destinationType.getType().getConstructor(new Class[0]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		return true;
	}

	private boolean canHandle(Class sourceObjectClass, Class destinationClass) {
		if (handleTargetClassSameAsSourceClass && sourceObjectClass.equals(destinationClass)) {
			return true;
		}
		BeanToBeanMapping beanToBeanMapping = beanToBeanMappings
				.get(new ClassPair(sourceObjectClass, destinationClass));
		return beanToBeanMapping != null;
	}

	@Override
	public boolean canHandle(ConversionContext context, Object sourceObject,
			Type destinationType) {
		if (sourceObject != null) {
			try {
				if (!canHandle(sourceObject.getClass(), destinationType
						.getType())) {
					return false;
				}
			} catch (ClassNotFoundException e) {
				return false;
			}
		}
		return super.canHandle(context, sourceObject, destinationType);
	}
}
