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

import net.entropysoft.transmorph.type.TypeReference;

/**
 * default implementation for {@link IBeanPropertyTypeProvider}
 * 
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class BeanPropertyTypeProvider implements IBeanPropertyTypeProvider {

	private Map<ClassProperty, TypeReference<?>> propertiesDestinationTypes = new HashMap<ClassProperty, TypeReference<?>>();

	/**
	 * set the property destination type for given property
	 * 
	 * @param propertyName
	 * @param destinationType
	 */
	public void setPropertyDestinationType(Class<?> clazz, String propertyName,
			TypeReference<?> destinationType) {
		propertiesDestinationTypes.put(new ClassProperty(clazz, propertyName), destinationType);
	}

	public TypeReference<?> getPropertyType(Class<?> clazz, String propertyName,
			TypeReference<?> originalType) {
		return propertiesDestinationTypes.get(new ClassProperty(clazz, propertyName));
	}

	private static class ClassProperty {
		final private Class<?> clazz;
		final private String propertyName;

		public ClassProperty(Class<?> clazz, String propertyName) {
			super();
			this.clazz = clazz;
			this.propertyName = propertyName;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
			result = prime * result
					+ ((propertyName == null) ? 0 : propertyName.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ClassProperty other = (ClassProperty) obj;
			if (clazz == null) {
				if (other.clazz != null)
					return false;
			} else if (!clazz.equals(other.clazz))
				return false;
			if (propertyName == null) {
				if (other.propertyName != null)
					return false;
			} else if (!propertyName.equals(other.propertyName))
				return false;
			return true;
		}
	}
	
}
