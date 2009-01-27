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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Maintains a list of all used converters for a given conversion
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class UsedConverters {
	// we use both a list and a set to keep order
	private List<UsedConverter> usedConvertersList = new ArrayList<UsedConverter>();
	private Set<UsedConverter> usedConvertersSet = new HashSet<UsedConverter>();

	/**
	 * add a converter that has been used to convert from sourceObject to
	 * destination type (if it has not already been added)
	 * 
	 * @param converter
	 * @param sourceObject
	 * @param destinationType
	 */
	public void addUsedConverter(IConverter converter, Object sourceObject,
			Type destinationType) {
		UsedConverter usedConverter = new UsedConverter(converter,
				sourceObject == null ? null : sourceObject.getClass(),
				destinationType);
		if (!usedConvertersSet.contains(usedConverter)) {
			usedConvertersSet.add(usedConverter);
			usedConvertersList.add(usedConverter);
		}
	}

	/**
	 * get all the converters that have been used for the conversion
	 * 
	 * @return
	 */
	public UsedConverter[] getUsedConverters() {
		return usedConvertersList.toArray(new UsedConverter[usedConvertersList
				.size()]);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (UsedConverter usedConverter : usedConvertersList) {
			sb
					.append(MessageFormat
							.format(
									"Converter ''{0}'' used to convert from ''{1}'' to destination type ''{2}''.",
									usedConverter.converter.getClass()
											.getSimpleName(),
									usedConverter.sourceObjectClass == null ? "null"
											: usedConverter.sourceObjectClass
													.getName(),
									usedConverter.destinationType.toString()));
			sb.append('\n');
		}
		return sb.toString();
	}

	private class UsedConverter {
		private IConverter converter;
		private Class sourceObjectClass;
		private Type destinationType;

		public UsedConverter(IConverter converter, Class sourceObjectClass,
				Type destinationType) {
			this.converter = converter;
			this.sourceObjectClass = sourceObjectClass;
			this.destinationType = destinationType;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
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
			if (!getOuterType().equals(other.getOuterType()))
				return false;
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

		private UsedConverters getOuterType() {
			return UsedConverters.this;
		}
	}

}
