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
 * Filter for bean properties
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public interface IBeanPropertyFilter {

	/**
	 * Whether to keep the property or not
	 * 
	 * @param propertyName
	 * @param getterMethod
	 * @param setterMethod
	 * @return true to keep the property, false otherwise
	 */
	public boolean filter(String propertyName, Method getterMethod,
			Method setterMethod);

}
