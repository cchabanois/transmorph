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

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used to convert a String to a Date
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToDate extends AbstractSimpleConverter<String, Date> {

	private DateFormat dateFormat = new SimpleDateFormat();

	public StringToDate() {
		super(String.class, Date.class);
		dateFormat.setLenient(false);
	}

	public synchronized DateFormat getDateFormat() {
		return dateFormat;
	}

	public synchronized void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public Date doConvert(IConverter elementConverter, String sourceObject,
			Type destinationType) throws ConverterException {
		ParsePosition pos = new ParsePosition(0);
		Date result;
		synchronized(this) {
			result = dateFormat.parse(sourceObject, pos);
		}
		if (pos.getIndex() < sourceObject.length()) {
			result = null;
		}

		if (result == null) {
			throw new ConverterException(MessageFormat.format(
					"Could not convert ''{0}'' to a date", sourceObject));
		}

		return result;

	}

}
