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

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.signature.JavaTypeToTypeSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used to convert a Map to a bean.
 * 
 * Map keys must be the property names
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MapToBean extends AbstractContainerConverter {

	private JavaTypeToTypeSignature javaTypeSignature = new JavaTypeToTypeSignature();
	private IBeanPropertyTypeProvider beanDestinationPropertyTypeProvider;

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

	public Object doConvert(Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		Map<String, Object> sourceMap = (Map<String, Object>) sourceObject;
		Map<String, Method> setterMethods;
		try {
			setterMethods = getSetterMethods(destinationType.getType());
		} catch (ClassNotFoundException e) {
			throw new ConverterException(MessageFormat.format(
					"Could not get setter methods for ''{0}''", destinationType
							.getName()), e);
		}

		Object resultBean;
		try {
			resultBean = destinationType.getType().newInstance();
		} catch (Exception e) {
			throw new ConverterException(MessageFormat.format(
					"Could not create instance of ''{0}''", destinationType
							.getName()), e);
		}

		for (String key : sourceMap.keySet()) {
			Object value = sourceMap.get(key);
			Method method = getSetterMethod(setterMethods, key);
			if (method == null) {
				throw new ConverterException(MessageFormat.format(
						"Could not find property ''{0}'' in {1}", key,
						destinationType.getName()));
			}
			java.lang.reflect.Type parameterType = method
					.getGenericParameterTypes()[0];
			TypeSignature parameterTypeSignature = javaTypeSignature
					.getTypeSignature(parameterType);
			Type originalType = destinationType.getTypeFactory().getType(
					parameterTypeSignature);
			Type propertyDestinationType = getBeanPropertyType(
					resultBean.getClass(), key, originalType);

			Object valueConverterd = elementConverter.convert(value,
					propertyDestinationType);

			try {
				method.invoke(resultBean, valueConverterd);
			} catch (Exception e) {
				throw new ConverterException("Could not set property for bean",
						e);
			}
		}

		return resultBean;
	}

	protected Type getBeanPropertyType(Class clazz, String propertyName,
			Type originalType) {
		Type propertyDestinationType = null;
		if (beanDestinationPropertyTypeProvider != null) {
			propertyDestinationType = beanDestinationPropertyTypeProvider
					.getPropertyType(clazz, propertyName,
							originalType);
		}
		if (propertyDestinationType == null) {
			propertyDestinationType = originalType;
		}
		return propertyDestinationType;
	}

	protected Method getSetterMethod(Map<String, Method> setterMethods,
			String propertyName) {
		String methodName = "set" + BeanUtils.capitalizePropertyName(propertyName);
		return setterMethods.get(methodName);
	}

	private Map<String, Method> getSetterMethods(Class clazz) {
		Map<String, Method> setters = new HashMap<String, Method>();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getParameterTypes().length == 1
					&& method.getName().startsWith("set")
					&& method.getReturnType() == Void.TYPE) {
				setters.put(method.getName(), method);
			}
		}
		return setters;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		try {
			destinationType.getType().getConstructor(new Class[0]);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		if (!(sourceObject instanceof Map)) {
			return false;
		}
		for (Object object : ((Map) sourceObject).keySet()) {
			if (!(object instanceof String)) {
				return false;
			}
		}
		return true;
	}

}
