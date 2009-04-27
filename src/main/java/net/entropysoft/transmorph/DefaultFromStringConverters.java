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
package net.entropysoft.transmorph;

import net.entropysoft.transmorph.converters.FormattedStringToNumber;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.StringToBoolean;
import net.entropysoft.transmorph.converters.StringToCalendar;
import net.entropysoft.transmorph.converters.StringToCharacterArray;
import net.entropysoft.transmorph.converters.StringToClass;
import net.entropysoft.transmorph.converters.StringToDate;
import net.entropysoft.transmorph.converters.StringToFile;
import net.entropysoft.transmorph.converters.StringToInputStream;
import net.entropysoft.transmorph.converters.StringToNumber;
import net.entropysoft.transmorph.converters.StringToStringBuffer;
import net.entropysoft.transmorph.converters.StringToStringBuilder;
import net.entropysoft.transmorph.converters.StringToTimeZone;
import net.entropysoft.transmorph.converters.StringToURI;
import net.entropysoft.transmorph.converters.StringToURL;
import net.entropysoft.transmorph.converters.enums.StringToEnum;
import net.entropysoft.transmorph.converters.propertyeditors.FromStringUsingPropertyEditor;

/**
 * Default converters when you only want to convert from {@link String}
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 */
public class DefaultFromStringConverters extends MultiConverter {
	private StringToEnum stringToEnum = new StringToEnum();
	private FromStringUsingPropertyEditor fromStringUsingPropertyEditor = new FromStringUsingPropertyEditor();
	private FormattedStringToNumber formattedStringToNumber = new FormattedStringToNumber();
	private StringToBoolean stringToBoolean = new StringToBoolean();
	private StringToCalendar stringToCalendar = new StringToCalendar();
	private StringToCharacterArray stringToCharacterArray = new StringToCharacterArray();
	private StringToClass stringToClass = new StringToClass();
	private StringToDate stringToDate = new StringToDate();
	private StringToFile stringToFile = new StringToFile();
	private StringToInputStream stringToInputStream = new StringToInputStream();
	private StringToNumber stringToNumber = new StringToNumber();
	private StringToStringBuffer stringToStringBuffer = new StringToStringBuffer();
	private StringToStringBuilder stringToStringBuilder = new StringToStringBuilder();
	private StringToTimeZone stringToTimeZone = new StringToTimeZone();
	private StringToURI stringToURI = new StringToURI();
	private StringToURL stringToURL = new StringToURL();
	
	public DefaultFromStringConverters() {
		super(true, new IConverter[0]);
		addConverter(stringToEnum);
		addConverter(fromStringUsingPropertyEditor);
		addConverter(formattedStringToNumber);
		addConverter(stringToBoolean);
		addConverter(stringToCalendar);
		addConverter(stringToCharacterArray);
		addConverter(stringToClass);
		addConverter(stringToDate);
		addConverter(stringToFile);
		addConverter(stringToInputStream);
		addConverter(stringToNumber);
		addConverter(stringToStringBuffer);
		addConverter(stringToStringBuilder);
		addConverter(stringToTimeZone);
		addConverter(stringToURI);
		addConverter(stringToURL);
	}

	public StringToEnum getStringToEnum() {
		return stringToEnum;
	}

	public FromStringUsingPropertyEditor getFromStringUsingPropertyEditor() {
		return fromStringUsingPropertyEditor;
	}

	public FormattedStringToNumber getFormattedStringToNumber() {
		return formattedStringToNumber;
	}

	public StringToBoolean getStringToBoolean() {
		return stringToBoolean;
	}

	public StringToCalendar getStringToCalendar() {
		return stringToCalendar;
	}

	public StringToCharacterArray getStringToCharacterArray() {
		return stringToCharacterArray;
	}

	public StringToClass getStringToClass() {
		return stringToClass;
	}

	public StringToDate getStringToDate() {
		return stringToDate;
	}

	public StringToFile getStringToFile() {
		return stringToFile;
	}

	public StringToInputStream getStringToInputStream() {
		return stringToInputStream;
	}

	public StringToNumber getStringToNumber() {
		return stringToNumber;
	}

	public StringToStringBuffer getStringToStringBuffer() {
		return stringToStringBuffer;
	}

	public StringToStringBuilder getStringToStringBuilder() {
		return stringToStringBuilder;
	}

	public StringToTimeZone getStringToTimeZone() {
		return stringToTimeZone;
	}

	public StringToURI getStringToURI() {
		return stringToURI;
	}

	public StringToURL getStringToURL() {
		return stringToURL;
	}
	
}
