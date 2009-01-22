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
package net.entropysoft.transmorph.converters;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.signature.JavaTypeToTypeSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used to convert a Bean to another bean.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class BeanToBean extends AbstractContainerConverter {

	private JavaTypeToTypeSignature javaTypeSignature = new JavaTypeToTypeSignature();
	private IBeanDestinationPropertyTypeProvider beanDestinationPropertyTypeProvider;

	public IBeanDestinationPropertyTypeProvider getBeanDestinationPropertyTypeProvider() {
		return beanDestinationPropertyTypeProvider;
	}

	/**
	 * set the provider for destination bean property types
	 * 
	 * @param beanDestinationPropertyTypeProvider
	 */
	public void setBeanDestinationPropertyTypeProvider(
			IBeanDestinationPropertyTypeProvider beanDestinationPropertyTypeProvider) {
		this.beanDestinationPropertyTypeProvider = beanDestinationPropertyTypeProvider;
	}

	public Object doConvert(Object sourceObject, Type destinationType)
			throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Map<String, Method> destinationSetterMethods;
		try {
			destinationSetterMethods = getDestinationSetterMethods(destinationType
					.getType());
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

		for (String destinationMethodName : destinationSetterMethods.keySet()) {
			Method destinationMethod = destinationSetterMethods.get(destinationMethodName);
			String sourceMethodName = "get" + destinationMethodName.substring(3);
			
			Method sourceMethod = getMethod(sourceObject.getClass(), sourceMethodName);
			
			if (sourceMethod == null) {
				sourceMethodName = "is" + destinationMethodName.substring(3);
				sourceMethod = getMethod(sourceObject.getClass(), sourceMethodName);
				if (sourceMethod.getReturnType() != Boolean.TYPE) {
					sourceMethod = null;
				}
			}
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
			TypeSignature parameterTypeSignature = javaTypeSignature
					.getTypeSignature(parameterType);
			Type originalType = destinationType.getTypeFactory().getType(
					parameterTypeSignature);
			Type propertyDestinationType = getPropertyDestinationType(
					resultBean.getClass(), destinationMethodName, originalType);

			Object destinationPropertyValue = elementConverter.convert(sourcePropertyValue,
					propertyDestinationType);

			try {
				destinationMethod.invoke(resultBean, destinationPropertyValue);
			} catch (Exception e) {
				throw new ConverterException("Could not set property for bean",
						e);
			}
		}

		return resultBean;
	}

	private Method getMethod(Class clazz, String name, Class<?>... parameterTypes) {
		try {
			return clazz.getMethod(name, parameterTypes);
		} catch (SecurityException e) {
			return null;
		} catch (NoSuchMethodException e) {
			return null;
		}
	}
	
	protected Type getPropertyDestinationType(Class clazz, String propertyName,
			Type originalType) {
		Type propertyDestinationType = null;
		if (beanDestinationPropertyTypeProvider != null) {
			propertyDestinationType = beanDestinationPropertyTypeProvider
					.getPropertyDestinationType(clazz, propertyName,
							originalType);
		}
		if (propertyDestinationType == null) {
			propertyDestinationType = originalType;
		}
		return propertyDestinationType;
	}

	private Map<String, Method> getDestinationSetterMethods(Class clazz) {
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
			// make sure that destinationType has a constructor with no
			// parameters
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
		// TODO : verify that
		return true;
	}

}
