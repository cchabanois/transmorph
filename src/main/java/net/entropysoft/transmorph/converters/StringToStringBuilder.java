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
package net.entropysoft.transmorph.converters;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source is a String and destination is a StringBuilder
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToStringBuilder extends AbstractSimpleConverter<String, StringBuilder> {

	public StringToStringBuilder() {
		super(String.class, StringBuilder.class);
	}

	@Override
	public StringBuilder doConvert(IConverter elementConverter,
			String sourceObject, Type destinationType)
			throws ConverterException {
		return new StringBuilder(sourceObject);
	}
}
