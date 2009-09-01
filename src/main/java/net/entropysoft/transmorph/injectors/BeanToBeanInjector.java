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
package net.entropysoft.transmorph.injectors;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.WrapperToPrimitive;
import net.entropysoft.transmorph.converters.beans.BeanToBeanMapping;
import net.entropysoft.transmorph.converters.beans.IBeanPropertyTypeProvider;
import net.entropysoft.transmorph.converters.beans.utils.BeanUtils;
import net.entropysoft.transmorph.converters.beans.utils.ClassPair;
import net.entropysoft.transmorph.type.Type;

/**
 * Copy properties from one bean to another bean
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class BeanToBeanInjector extends AbstractBeanInjector {
	private IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider;
	private Map<ClassPair, BeanToBeanMapping> beanToBeanMappings = new HashMap<ClassPair, BeanToBeanMapping>();
	private boolean handleTargetClassSameAsSourceClass = true;

	public BeanToBeanInjector() {
		// by default, we don't do any conversion
		propertyValueConverter = new MultiConverter(new IdentityConverter(),
				new WrapperToPrimitive());
	}

	public IBeanPropertyTypeProvider getBeanDestinationPropertyTypeProvider() {
		return beanDestinationPropertyTypeProvider;
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

	public boolean isHandleTargetClassSameAsSourceClass() {
		return handleTargetClassSameAsSourceClass;
	}

	/**
	 * By default, BeanToBeanInjector can handle the case where target class is
	 * the same as source class. If you don't want it to handle this case, you
	 * can set this property to false
	 * 
	 * @param useIfTargetClassSameAsSourceClass
	 */
	public void setHandleTargetClassSameAsSourceClass(
			boolean useIfTargetClassSameAsSourceClass) {
		this.handleTargetClassSameAsSourceClass = useIfTargetClassSameAsSourceClass;
	}

	public boolean canHandle(Object sourceObject, Type targetType) {
		try {
			return canHandle(sourceObject.getClass(), targetType.getType());
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public void inject(ConversionContext context, Object sourceObject,
			Object targetBean, Type targetType) throws ConverterException {
		Class destinationClass;
		try {
			destinationClass = targetType.getType();
		} catch (ClassNotFoundException e) {
			throw new ConverterException(
					"Could not get destination type class", e);
		}

		if (!canHandle(sourceObject.getClass(), destinationClass)) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Could not get bean to bean mapping for ''{0}''=>''{1}''",
									sourceObject.getClass().getName(),
									destinationClass.getName()));
		}

		// get destination property => method
		Map<String, Method> destinationSetters;
		destinationSetters = BeanUtils.getSetters(destinationClass);

		for (String destinationPropertyName : destinationSetters.keySet()) {
			Method destinationMethod = destinationSetters
					.get(destinationPropertyName);

			Method sourceMethod = getPropertySourceMethod(sourceObject,
					targetBean, destinationPropertyName);

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
			Type originalType = targetType.getTypeFactory().getType(
					parameterType);
			Type propertyDestinationType = getBeanPropertyType(
					destinationClass, destinationPropertyName, originalType);

			Object destinationPropertyValue = propertyValueConverter.convert(
					context, sourcePropertyValue, propertyDestinationType);

			try {
				destinationMethod.invoke(targetBean, destinationPropertyValue);
			} catch (Exception e) {
				throw new ConverterException("Could not set property for bean",
						e);
			}
		}
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
		return BeanUtils.getGetterPropertyMethod(sourceObject.getClass(),
				sourceProperty);
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

	private boolean canHandle(Class sourceObjectClass, Class destinationClass) {
		if (handleTargetClassSameAsSourceClass
				&& sourceObjectClass.equals(destinationClass)) {
			return true;
		}
		BeanToBeanMapping beanToBeanMapping = beanToBeanMappings
				.get(new ClassPair(sourceObjectClass, destinationClass));
		return beanToBeanMapping != null;
	}

}
