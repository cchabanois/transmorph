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

import java.util.Locale;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;

/**
 * Convert a String to an uppercased string
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class StringToUppercasedString extends AbstractSimpleConverter<String,String> {

	private Locale locale;
	
	public StringToUppercasedString() {
		super(String.class, String.class);
	}

	@Override
	public String doConvert(String sourceObject, Type destinationType) throws ConverterException {
		if (locale == null) {
			return sourceObject.toUpperCase();
		} else {
			return sourceObject.toUpperCase(locale);
		}
	}

}
