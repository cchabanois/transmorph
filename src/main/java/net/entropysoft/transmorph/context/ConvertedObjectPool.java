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
package net.entropysoft.transmorph.context;

import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Pool of created objects by converters that use object pool
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ConvertedObjectPool {

	private Map<ConvertedObjectsKey, Object> convertedObjects = new HashMap<ConvertedObjectsKey, Object>();

	/**
	 * get the converted object corresponding to sourceObject as converted to
	 * destination type by converter
	 * 
	 * @param converter
	 * @param sourceObject
	 * @param destinationType
	 * @return
	 */
	public Object get(IConverter converter, Object sourceObject,
			Type destinationType) {
		return convertedObjects.get(new ConvertedObjectsKey(converter,
				sourceObject, destinationType));
	}

	/**
	 * add a converted object to the pool
	 * 
	 * @param converter
	 *            the converter that made the conversion
	 * @param sourceObject
	 *            the source object that has been converted
	 * @param destinationType
	 *            the destination type
	 * @param convertedObject
	 *            the converted object
	 */
	public void add(IConverter converter, Object sourceObject,
			Type destinationType, Object convertedObject) {
		convertedObjects.put(new ConvertedObjectsKey(converter, sourceObject,
				destinationType), convertedObject);
	}

	/**
	 * remove a converted object from the pool
	 * 
	 * @param converter
	 * @param sourceObject
	 * @param destinationType
	 */
	public void remove(IConverter converter, Object sourceObject,
			Type destinationType) {
		convertedObjects.remove(new ConvertedObjectsKey(converter,
				sourceObject, destinationType));
	}

	private static class ConvertedObjectsKey {
		private IConverter converter;
		private Object sourceObject;
		private Type destinationType;

		public ConvertedObjectsKey(IConverter converter, Object sourceObject,
				Type destinationType) {
			this.converter = converter;
			this.sourceObject = sourceObject;
			this.destinationType = destinationType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((converter == null) ? 0 : converter.hashCode());
			result = prime
					* result
					+ ((destinationType == null) ? 0 : destinationType
							.hashCode());
			result = prime * result
					+ ((sourceObject == null) ? 0 : sourceObject.hashCode());
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
			ConvertedObjectsKey other = (ConvertedObjectsKey) obj;
			if (converter == null) {
				if (other.converter != null)
					return false;
			} else if (!converter.equals(other.converter))
				return false;
			if (destinationType == null) {
				if (other.destinationType != null)
					return false;
			} else if (!destinationType.equals(other.destinationType))
				return false;
			if (sourceObject == null) {
				if (other.sourceObject != null)
					return false;
			} else if (sourceObject != other.sourceObject)
				return false;
			return true;
		}

	}

}
