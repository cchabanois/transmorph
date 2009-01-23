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
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source object is a String and destination type is boolean
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class StringToBoolean extends AbstractConverter {

	private String trueString = "true";
	private String falseString = "false";
	private boolean caseSensitive = true;

	public String getTrueString() {
		return trueString;
	}

	public void setTrueString(String trueString) {
		this.trueString = trueString;
	}

	public String getFalseString() {
		return falseString;
	}

	public void setFalseString(String falseString) {
		this.falseString = falseString;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public Object doConvert(Object sourceObject, Type destinationType) throws ConverterException {
		if (sourceObject == null) {
			if (destinationType.isPrimitive()) {
				throw new ConverterException("Could not convert null to boolean primitive type");
			}
			return null;
		}
		String sourceString = (String) sourceObject;

		if (caseSensitive) {
			if (sourceString.equals(trueString)) {
				return true;
			} else if (sourceString.equals(falseString)) {
				return false;
			} else {
				throw new ConverterException("Could not convert '"
						+ sourceString + "' to a boolean");
			}
		} else {
			if (sourceString.equalsIgnoreCase(trueString)) {
				return true;
			} else if (sourceString.equalsIgnoreCase(falseString)) {
				return false;
			} else {
				throw new ConverterException("Could not convert '"
						+ sourceString + "' to a boolean");
			}
		}
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.getType().equals(Boolean.TYPE) || destinationType.getType().equals(Boolean.class);
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
