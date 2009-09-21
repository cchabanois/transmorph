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

import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter that has been used for a given conversion
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class UsedConverter {

	private IConverter converter;
	private Class<?> sourceObjectClass;
	private TypeReference<?> destinationType;

	public UsedConverter(IConverter converter, Class<?> sourceObjectClass,
			TypeReference<?> destinationType) {
		this.converter = converter;
		this.sourceObjectClass = sourceObjectClass;
		this.destinationType = destinationType;
	}

	public IConverter getConverter() {
		return converter;
	}

	public Class<?> getSourceObjectClass() {
		return sourceObjectClass;
	}

	public TypeReference<?> getDestinationType() {
		return destinationType;
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
		result = prime
				* result
				+ ((sourceObjectClass == null) ? 0 : sourceObjectClass
						.hashCode());
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
		UsedConverter other = (UsedConverter) obj;
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
		if (sourceObjectClass == null) {
			if (other.sourceObjectClass != null)
				return false;
		} else if (!sourceObjectClass.equals(other.sourceObjectClass))
			return false;
		return true;
	}

}