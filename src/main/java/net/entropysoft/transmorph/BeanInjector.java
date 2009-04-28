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

import java.lang.reflect.Method;
import java.util.Map;

import net.entropysoft.transmorph.converters.beans.utils.BeanUtils;

/**
 * Inject property values into a bean after conversion.
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class BeanInjector {

	private Transmorph transmorph;
	private Object bean;
	private Map<String, Method> setters;

	public BeanInjector(Object bean, Transmorph transmorph) {
		this.bean = bean;
		this.transmorph = transmorph;
	}

	public void inject(String propertyName, Object value) throws ConverterException {
		Map<String, Method> setters = getSetters();
		Method setter = setters.get(propertyName);
		java.lang.reflect.Type parameterType = setter
				.getGenericParameterTypes()[0];
		Object convertedValue = transmorph.convert(value, parameterType);
		
		try {
			setter.invoke(bean, convertedValue);
		} catch (Exception e) {
			throw new ConverterException("Could not set property for bean", e);
		}
	}

	private Map<String, Method> getSetters() {
		if (setters == null) {
			setters = BeanUtils.getSetters(bean.getClass());
		}
		return setters;
	}

	public void inject(Map<String, Object> properties) throws ConverterException {
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			inject(entry.getKey(), entry.getValue());
		}
	}

}
