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

public interface IBeanToMapMapping {

	/**
	 * get the map key we will use as target for given bean property.
	 * 
	 * @param bean
	 * @param propertyName
	 * @param propertyValue
	 * @param getterMethod
	 * @param setterMethod
	 * @return the map key or null to ignore this property
	 */
	public String getMapKey(Object bean, String propertyName,
			Object propertyValue, Method getterMethod, Method setterMethod);

}
