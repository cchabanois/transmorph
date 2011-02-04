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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Default mapper for bean properties. It only considers properties that have
 * both a setter and a getter
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class DefaultBeanToMapMapping implements IBeanToMapMapping2 {

	private boolean ignorePropertiesWithNoSetter = true;
	private boolean keepClass = false;
	private boolean ignoreNullValues = false;
	
	
	public Map<String, Object> getOtherValues(Object bean) {
		if (keepClass) {
			Map<String, Object> otherValues = new HashMap<String, Object>(1);
			otherValues.put("class", bean.getClass());
			return otherValues;
		} else {
			return Collections.emptyMap();
		}
	}
	
	public boolean select(Object bean, String propertyName, Method getterMethod, Method setterMethod) {
		if ("class".equals(propertyName)) {
			return false;
		}
		if (ignorePropertiesWithNoSetter && setterMethod == null) {
			return false;
		}
		return true;
	}
	
	public String getMapKey(Object bean, String propertyName,
			Object propertyValue, Method getterMethod, Method setterMethod) {
		if (propertyValue == null && ignoreNullValues) {
			return null;
		}
		return propertyName;
	}
	
	public void setIgnorePropertiesWithNoSetter(
			boolean ignorePropertiesWithNoSetter) {
		this.ignorePropertiesWithNoSetter = ignorePropertiesWithNoSetter;
	}
	
	public void setIgnoreNullValues(boolean ignoreNullValues) {
		this.ignoreNullValues = ignoreNullValues;
	}
	
	public boolean isIgnorePropertiesWithNoSetter() {
		return ignorePropertiesWithNoSetter;
	}
	
	public void setKeepClass(boolean keepClass) {
		this.keepClass = keepClass;
	}
	
	public boolean isKeepClass() {
		return keepClass;
	}
	
}
