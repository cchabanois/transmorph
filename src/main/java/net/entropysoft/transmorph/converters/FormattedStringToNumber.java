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

import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeUtils;

/**
 * Convert a formatted String to a Number using NumberFormat
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class FormattedStringToNumber extends AbstractConverter {

	private NumberFormat numberFormat = NumberFormat.getInstance();
	private NumberToNumber numberToNumberConverter = new NumberToNumber();

	public FormattedStringToNumber() {
		this.useObjectPool = true;
	}
	
	public NumberFormat getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(NumberFormat numberFormat) {
		this.numberFormat = numberFormat;
	}

	public Object doConvert(ConversionContext context, Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException("Cannot convert null to a primitive number");
			}
			return null;
		}

		String sourceString = (String) sourceObject;

		ParsePosition pos = new ParsePosition(0);
		Number number;
		synchronized(this) {
			number = numberFormat.parse(sourceString, pos);
		}
		if (pos.getIndex() < sourceString.length()) {
			number = null;
		}

		if (number == null) {
			throw new ConverterException(MessageFormat.format(
					"Could not convert ''{0}'' to a number", sourceString));
		}

		return numberToNumberConverter.convert(context, number, destinationType);
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return TypeUtils.isNumberType(destinationType);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof String;
	}

}
