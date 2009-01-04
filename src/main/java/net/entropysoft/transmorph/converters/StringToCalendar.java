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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used to convert a String to a Calendar or a GregorianCalendar
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToCalendar implements IConverter {

	private DateFormat dateFormat = new SimpleDateFormat();

	public StringToCalendar() {
		dateFormat.setLenient(false);
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public Object convert(IConverter elementConverter, Object sourceObject,
			Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		String sourceString = (String) sourceObject;

		ParsePosition pos = new ParsePosition(0);
		Date date = dateFormat.parse(sourceString, pos);
		if (pos.getIndex() < sourceString.length()) {
			date = null;
		}

		if (date == null) {
			throw new ConverterException(MessageFormat.format(
					"Could not convert ''{0}'' to a date", sourceString));
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		return calendar;
	}

	public boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.isType(Calendar.class) || destinationType.isType(GregorianCalendar.class);
		} catch (ClassNotFoundException e) {
			return false;
		}

	}

	public boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof String;
	}

}
