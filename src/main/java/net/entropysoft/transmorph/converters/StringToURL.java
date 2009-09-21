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

import java.net.MalformedURLException;
import java.net.URL;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter used when source is a String and destination is an URL
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToURL extends AbstractSimpleConverter<String, URL> {

	public StringToURL() {
		super(String.class, URL.class);
		this.useObjectPool = true;
	}

	@Override
	public URL doConvert(ConversionContext context, String sourceObject, TypeReference<?> destinationType) throws ConverterException {
		try {
			return new URL(sourceObject);
		} catch (MalformedURLException e) {
			throw new ConverterException("Could not convert String to URL", e);
		}

	}

}
