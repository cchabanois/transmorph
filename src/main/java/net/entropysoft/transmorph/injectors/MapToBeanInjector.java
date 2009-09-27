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
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.WrapperToPrimitive;
import net.entropysoft.transmorph.converters.beans.IBeanPropertyTypeProvider;
import net.entropysoft.transmorph.type.TypeReference;
import net.entropysoft.transmorph.utils.BeanUtils;

/**
 * Copy values from map to bean
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MapToBeanInjector extends AbstractBeanInjector {
	private IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider;

	public MapToBeanInjector() {
		// by default, we don't do any conversion : we just convert wrappers to
		// primitives
		propertyValueConverter = new MultiConverter(new IdentityConverter(),
				new WrapperToPrimitive());
	}

	public void setBeanDestinationPropertyTypeProvider(
			IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider) {
		this.beanDestinationPropertyTypeProvider = beanDestinationPropertyTypeProvider;
	}

	public IBeanPropertyTypeProvider getBeanDestinationPropertyTypeProvider() {
		return beanDestinationPropertyTypeProvider;
	}

	public boolean canHandle(Object sourceObject, TypeReference<?> targetType) {
		if (!(sourceObject instanceof Map<?,?>)) {
			return false;
		}
		for (Object object : ((Map<?,?>) sourceObject).keySet()) {
			if (!(object instanceof String)) {
				return false;
			}
		}
		return true;
	}

	public void inject(ConversionContext context, Object sourceObject,
			Object targetBean, TypeReference<?> targetType)
			throws ConverterException {
		Map<String, Object> sourceMap = (Map<String, Object>) sourceObject;
		Map<String, Method> setterMethods;
		Class<?> targetTypeClass = targetType.getRawType();
		setterMethods = BeanUtils.getSetters(targetTypeClass);

		for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			Method method = setterMethods.get(key);
			if (method == null) {
				throw new ConverterException(MessageFormat.format(
						"Could not find property ''{0}'' in ''{1}''", key,
						targetType.toHumanString()));
			}
			java.lang.reflect.Type parameterType = method
					.getGenericParameterTypes()[0];
			TypeReference<?> originalType = TypeReference.get(parameterType);
			TypeReference<?> propertyDestinationType = getBeanPropertyType(
					targetTypeClass, key, originalType);

			Object valueConverted = propertyValueConverter.convert(context,
					value, propertyDestinationType);

			try {
				method.invoke(targetBean, valueConverted);
			} catch (Exception e) {
				throw new ConverterException("Could not set property for bean",
						e);
			}
		}
	}

	protected TypeReference<?> getBeanPropertyType(Class<?> targetClass,
			String propertyName, TypeReference<?> originalType) {
		TypeReference<?> propertyDestinationType = null;
		if (beanDestinationPropertyTypeProvider != null) {
			propertyDestinationType = beanDestinationPropertyTypeProvider
					.getPropertyType(targetClass, propertyName, originalType);
		}
		if (propertyDestinationType == null) {
			propertyDestinationType = originalType;
		}
		return propertyDestinationType;
	}

}
