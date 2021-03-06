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
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.TypeReference;
import net.entropysoft.transmorph.utils.BeanUtils;
import net.entropysoft.transmorph.utils.ImmutableClasses;

/**
 * Converter used to convert a Map to a bean.
 * 
 * Map keys must be the property names
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MapToBean extends AbstractContainerConverter {
	private IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider;
	private IMapToBeanMapping mapToBeanMapping = new DefaultMapToBeanMapping();

	public MapToBean() {
		this.useObjectPool = true;
	}

	public void setMapToBeanMapping(IMapToBeanMapping mapToBeanMapping) {
		this.mapToBeanMapping = mapToBeanMapping;
	}

	public IBeanPropertyTypeProvider getBeanDestinationPropertyTypeProvider() {
		return beanDestinationPropertyTypeProvider;
	}

	/**
	 * set the provider for destination bean property types
	 * 
	 * @param beanDestinationPropertyTypeProvider
	 */
	public void setBeanPropertyTypeProvider(
			IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider) {
		this.beanDestinationPropertyTypeProvider = beanDestinationPropertyTypeProvider;
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		Map<String, Object> sourceMap = (Map<String, Object>) sourceObject;
		TypeReference<?> concreteDestinationType = mapToBeanMapping
				.getConcreteDestinationType(sourceMap, destinationType);
		if (concreteDestinationType == null) {
			throw new ConverterException(
					"Could not find concrete destination type for '"
							+ destinationType.toHumanString() + "'");
		}
		Map<String, Method> setterMethods = BeanUtils
				.getSetters(concreteDestinationType.getRawType());

		Object resultBean = newBeanInstance(concreteDestinationType);
		if (useObjectPool) {
			context.getConvertedObjectPool().add(this, sourceObject,
					destinationType, resultBean);
		}

		for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			String propertyName = mapToBeanMapping.getPropertyName(sourceMap,
					key, resultBean, setterMethods);
			if (propertyName == null) {
				continue;
			}
			Method method = setterMethods.get(propertyName);
			if (method == null) {
				throw new ConverterException(MessageFormat.format(
						"Could not find property ''{0}'' in ''{1}''",
						propertyName, concreteDestinationType.toHumanString()));
			}
			java.lang.reflect.Type parameterType = method
					.getGenericParameterTypes()[0];
			TypeReference<?> originalType = TypeReference.get(parameterType);
			TypeReference<?> propertyDestinationType = getBeanPropertyType(
					resultBean.getClass(), propertyName, originalType);

			Object valueConverted = elementConverter.convert(context, value,
					propertyDestinationType);

			try {
				method.invoke(resultBean, valueConverted);
			} catch (Exception e) {
				throw new ConverterException("Could not set property for bean",
						e);
			}
		}

		return resultBean;
	}

	protected Object newBeanInstance(TypeReference<?> destinationType)
			throws ConverterException {
		try {
			return destinationType.getRawType().newInstance();
		} catch (Exception e) {
			throw new ConverterException(MessageFormat.format(
					"Could not create instance of ''{0}''", destinationType
							.toHumanString()), e);
		}
	}

	protected TypeReference<?> getBeanPropertyType(Class<?> clazz,
			String propertyName, TypeReference<?> originalType) {
		TypeReference<?> propertyDestinationType = null;
		if (beanDestinationPropertyTypeProvider != null) {
			propertyDestinationType = beanDestinationPropertyTypeProvider
					.getPropertyType(clazz, propertyName, originalType);
		}
		if (propertyDestinationType == null) {
			propertyDestinationType = originalType;
		}
		return propertyDestinationType;
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		// don't try in these cases
		return !destinationType.isPrimitive()
				&& !destinationType.isArray()
				&& !ImmutableClasses.isKnownImmutableClass(destinationType
						.getRawType());
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		if (!(sourceObject instanceof Map<?, ?>)) {
			return false;
		}
		for (Object object : ((Map<?, ?>) sourceObject).keySet()) {
			if (!(object instanceof String)) {
				return false;
			}
		}
		return true;
	}

}
