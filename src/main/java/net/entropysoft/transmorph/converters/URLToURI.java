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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source is a URL and destination is an URI
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class URLToURI extends AbstractSimpleConverter<URL, URI> {

	public URLToURI() {
		super(URL.class, URI.class);
		this.useObjectPool = true;
	}

	@Override
	public URI doConvert(ConversionContext context, URL sourceObject, Type destinationType) throws ConverterException {
		try {
			return sourceObject.toURI();
		} catch (URISyntaxException e) {
			throw new ConverterException("Could not convert url to uri",e);
		}
	}

}
