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

import java.util.HashMap;
import java.util.Map;

/**
 * Property mappings between two beans
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * @see BeanToBean
 * 
 */
public class BeanToBeanMapping {

	private Class<?> sourceClass;
	private Class<?> destinationClass;
	private Map<String, String> destinationPropertyName2SourcePropertyNameMap = new HashMap<String, String>();

	public BeanToBeanMapping(Class<?> sourceClass, Class<?> destinationClass) {
		this.sourceClass = sourceClass;
		this.destinationClass = destinationClass;
	}

	public Class<?> getSourceClass() {
		return sourceClass;
	}

	public Class<?> getDestinationClass() {
		return destinationClass;
	}

	/**
	 * Add a mapping between a source property and a destination property
	 * 
	 * @param sourceProperty 
	 * @param destinationProperty
	 */
	public void addMapping(String sourceProperty, String destinationProperty) {
		destinationPropertyName2SourcePropertyNameMap.put(destinationProperty,
				sourceProperty);
	}

	public String getSourceProperty(String destinationProperty) {
		return destinationPropertyName2SourcePropertyNameMap
				.get(destinationProperty);
	}

}
