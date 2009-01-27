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

import java.io.File;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source is a String and destination is a File
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToFile extends AbstractSimpleConverter<String, File> {

	public StringToFile() {
		super(String.class, File.class);
		this.useObjectPool = true; // immutable
	}

	@Override
	public File doConvert(ConversionContext context, String sourceObject, Type destinationType)
			throws ConverterException {
		File file = new File(sourceObject);
		return file;
	}

}
