/*
 * Copyright 2008-2010 the original author or authors.
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
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.TypeReference;
import net.entropysoft.transmorph.utils.BeanUtils;

/**
 * Converter used to convert a Bean to a map.
 * 
 * By default, map keys will be the property names
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class BeanToMap extends AbstractContainerConverter {
	@SuppressWarnings("unchecked")
	private Class<? extends Map> defaultMapClass = HashMap.class;
	private IBeanToMapMapping beanToMapMapping = new DefaultBeanToMapMapping();

	@SuppressWarnings("unchecked")
	private Set<Class<?>> excludedClasses = new HashSet<Class<?>>(
			Arrays.asList(String.class, Map.class, Collection.class,
					Calendar.class, Date.class));
	private boolean failIfNoGetters = true;

	public BeanToMap() {
		this.useObjectPool = true;
	}

	public void setBeanToMapMapping(IBeanToMapMapping beanToMapMapping) {
		this.beanToMapMapping = beanToMapMapping;
	}

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		if (!destinationType.isRawTypeSubOf(Map.class)) {
			return false;
		}

		TypeReference<?>[] destinationTypeArguments = destinationType
				.getTypeArguments();
		if (destinationTypeArguments.length > 0) {
			// must be Map<String, ...> or Map<Object, ...>
			if (!destinationTypeArguments[0].isType(String.class)
					&& destinationTypeArguments[0].isType(Object.class)) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		try {
			sourceObject.getClass().getConstructor(new Class[0]);
		} catch (Exception e) {
			return false;
		}

		if (sourceObject.getClass().isArray()) {
			return false;
		}
		for (Class<?> excludedClass : excludedClasses) {
			if (excludedClass.isInstance(sourceObject)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}

		Map<Object, Object> destinationMap;
		try {
			destinationMap = createDestinationMap(destinationType);
		} catch (Exception e) {
			throw new ConverterException("Could not create destination map", e);
		}
		if (destinationMap == null) {
			throw new ConverterException("Could not create destination map");
		}

		if (useObjectPool) {
			context.getConvertedObjectPool().add(this, sourceObject,
					destinationType, destinationMap);
		}

		TypeReference<?>[] destinationTypeArguments = getDestinationTypeArguments(destinationType);
		Map<String, Method> getterMethods = BeanUtils.getGetters(sourceObject
				.getClass());
		Map<String, Method> setterMethods = BeanUtils.getSetters(sourceObject
				.getClass());
		if (failIfNoGetters && getterMethods.size() == 1) {
			// only getClass()
			throw new ConverterException("No getters defined for source object");
		}

		for (Map.Entry<String, Method> entry : getterMethods.entrySet()) {
			String propertyName = entry.getKey();
			Method getterMethod = entry.getValue();
			Method setterMethod = setterMethods.get(propertyName);

			if (selectProperty(sourceObject, propertyName, getterMethod,
					setterMethod)) {
				Object sourcePropertyValue;
				try {
					sourcePropertyValue = getterMethod.invoke(sourceObject);
				} catch (Exception e) {
					throw new ConverterException(
							MessageFormat.format(
									"Could not get property ''{0}'' for bean with class ''{1}''",
									propertyName, sourceObject.getClass()
											.getName()), e);
				}

				String mapKey = beanToMapMapping.getMapKey(sourceObject,
						propertyName, sourcePropertyValue, getterMethod,
						setterMethod);
				if (mapKey != null) {
					Object value = elementConverter.convert(context,
							sourcePropertyValue, destinationTypeArguments[1]);
					destinationMap.put(mapKey, value);
				}
			}
		}
		Map<String, Object> otherValues = beanToMapMapping
				.getOtherValues(sourceObject);
		for (Map.Entry<String, Object> entry : otherValues.entrySet()) {
			Object value = elementConverter.convert(context, entry.getValue(),
					destinationTypeArguments[1]);
			destinationMap.put(entry.getKey(), value);
		}

		return destinationMap;
	}

	private boolean selectProperty(Object bean, String propertyName,
			Method getterMethod, Method setterMethod) {
		if (beanToMapMapping instanceof IBeanToMapMapping2) {
			return ((IBeanToMapMapping2) beanToMapMapping).select(bean,
					propertyName, getterMethod, setterMethod);
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	private Map<Object, Object> createDestinationMap(
			TypeReference<?> destinationType) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		Class<? extends Map> clazz = getConcreteMapDestinationClass(destinationType);
		if (clazz == null) {
			return null;
		}
		return clazz.newInstance();
	}

	@SuppressWarnings("unchecked")
	protected Class<? extends Map> getConcreteMapDestinationClass(
			TypeReference<?> destinationType) throws ClassNotFoundException {
		if (destinationType.hasRawType(Map.class)) {
			return defaultMapClass;
		}
		Class destinationClass = destinationType.getRawType();
		if (destinationClass.isInterface()
				|| Modifier.isAbstract(destinationClass.getModifiers())) {
			return null;
		}
		try {
			destinationClass.getConstructor(new Class[0]);
		} catch (Exception e) {
			return null;
		}
		return destinationClass;
	}

	protected TypeReference<?>[] getDestinationTypeArguments(
			TypeReference<?> mapDestinationType) {
		if (mapDestinationType.isRawTypeSubOf(Properties.class)) {
			// Properties extends Hashtable<Object,Object> but should contain
			// only strings
			return new TypeReference<?>[] { TypeReference.get(String.class),
					TypeReference.get(String.class) };
		}
		TypeReference<?>[] destinationTypeArguments = mapDestinationType
				.getTypeArguments();
		if (destinationTypeArguments.length == 0) {
			destinationTypeArguments = new TypeReference[] {
					TypeReference.get(Object.class),
					TypeReference.get(Object.class) };
		}
		return destinationTypeArguments;
	}

	/**
	 * Get the set of classes that will not be considered by this converter.
	 * 
	 * @return
	 */
	public Set<Class<?>> getExcludedClasses() {
		return excludedClasses;
	}

	/**
	 * Set the set of classes that will not be considered by this converter. All
	 * classes that do not have a public default constructor will not be
	 * considered and should not be be added there.
	 * 
	 * @param excludedClasses
	 */
	public void setExcludedClasses(Set<Class<?>> excludedClasses) {
		this.excludedClasses = excludedClasses;
	}

	/**
	 * Set whether this converter should fail if there are no getters
	 * 
	 * @param failIfNoGetters
	 */
	public void setFailIfNoGetters(boolean failIfNoGetters) {
		this.failIfNoGetters = failIfNoGetters;
	}

	public boolean isFailIfNoGetters() {
		return failIfNoGetters;
	}

}
