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
package net.entropysoft.transmorph.converters.collections;

import java.util.Iterator;
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.AbstractContainerConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class MapToString extends AbstractContainerConverter {
	private IStringMapFormatter stringMapFormatter = new DefaultStringMapFormatter();
	private IConverter keyConverter;
	private IConverter valueConverter;
	
	public MapToString() {
		this.useObjectPool = false;
	}
	
	public IConverter getKeyConverter() {
		return keyConverter;
	}

	public void setKeyConverter(IConverter keyConverter) {
		this.keyConverter = keyConverter;
	}

	public IConverter getValueConverter() {
		return valueConverter;
	}

	public void setValueConverter(IConverter valueConverter) {
		this.valueConverter = valueConverter;
	}

	public IStringMapFormatter getStringMapFormatter() {
		return stringMapFormatter;
	}

	public void setStringMapFormatter(IStringMapFormatter stringMapFormatter) {
		this.stringMapFormatter = stringMapFormatter;
	}

	public Object doConvert(ConversionContext context, Object sourceObject, TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		Map<Object, Object> sourceMap = (Map<Object, Object>) sourceObject;
		
		String[] keyStrings = new String[sourceMap.size()];
		String[] valueStrings = new String[sourceMap.size()];
		
		int i = 0;
		for (Iterator<Map.Entry<Object, Object>> it = sourceMap.entrySet()
				.iterator(); it.hasNext();) {
			Map.Entry<Object, Object> mapEntry = it.next();
			IConverter converter = keyConverter;
			if (converter == null) {
				converter = elementConverter;
			}
			String key = (String)converter.convert(context, mapEntry
							.getKey(), destinationType);
			
			converter = valueConverter;
			if (converter == null) {
				converter = elementConverter;
			}
			
			String value = (String)converter.convert(context, mapEntry
							.getValue(), destinationType);
			
			keyStrings[i] = key;
			valueStrings[i] = value;
			
			i++;
		}

		return stringMapFormatter.format(keyStrings, valueStrings);
	}

	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return destinationType.isType(String.class);
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}

		return sourceObject instanceof Map;
	}

}
