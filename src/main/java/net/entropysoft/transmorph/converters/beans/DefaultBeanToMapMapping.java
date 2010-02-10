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

/**
 * Default mapper for bean properties. It only considers properties that have
 * both a setter and a getter
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class DefaultBeanToMapMapping implements IBeanToMapMapping {

	private boolean ignorePropertiesWithNoSetter = true;
	private boolean keepClass = false;
	
	public String getMapKey(Object bean, String propertyName,
			Object propertyValue, Method getterMethod, Method setterMethod) {
		if ("class".equals(propertyName)) {
			if (keepClass) {
				return propertyName;
			} else {
				return null;
			}
		}
		if (propertyValue == null) {
			return null;
		}
		if (ignorePropertiesWithNoSetter && setterMethod == null) {
			return null;
		}
		return propertyName;
	}
	
	public void setIgnorePropertiesWithNoSetter(
			boolean ignorePropertiesWithNoSetter) {
		this.ignorePropertiesWithNoSetter = ignorePropertiesWithNoSetter;
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
