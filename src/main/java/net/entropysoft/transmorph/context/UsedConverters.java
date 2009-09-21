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
import net.entropysoft.transmorph.signature.formatter.ITypeSignatureFormatter;
import net.entropysoft.transmorph.type.TypeReference;

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
	 * Removes all the elements
	 */
	public void clear() {
		usedConvertersList.clear();
		usedConvertersSet.clear();
	}

	/**
	 * add a converter that has been used to convert from sourceObject to
	 * destination type (if it has not already been added)
	 * 
	 * @param converter
	 * @param sourceObject
	 * @param destinationType
	 */
	public void addUsedConverter(IConverter converter, Object sourceObject,
			TypeReference<?> destinationType) {
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
									usedConverter.getConverter().getClass()
											.getSimpleName(),
									usedConverter.getSourceObjectClass() == null ? "null"
											: usedConverter
													.getSourceObjectClass()
													.getName(), usedConverter
											.getDestinationType().toString()));
			sb.append('\n');
		}
		return sb.toString();
	}

	public String toString(ITypeSignatureFormatter formatter) {
		StringBuilder sb = new StringBuilder();
		for (UsedConverter usedConverter : usedConvertersList) {
//			String destinationName = formatter.format(usedConverter
//					.getDestinationType().getTypeSignature());
			String destinationName = usedConverter.getDestinationType().getName();
			sb
					.append(MessageFormat
							.format(
									"Converter ''{0}'' used to convert from ''{1}'' to destination type ''{2}''.",
									usedConverter.getConverter().getClass()
											.getSimpleName(),
									usedConverter.getSourceObjectClass() == null ? "null"
											: usedConverter
													.getSourceObjectClass()
													.getName(), destinationName));
			sb.append('\n');
		}
		return sb.toString();
	}

}
