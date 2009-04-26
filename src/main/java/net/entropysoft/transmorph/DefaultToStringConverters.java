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

import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;

import net.entropysoft.transmorph.converters.CharacterArrayToString;
import net.entropysoft.transmorph.converters.ClassToString;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.ObjectToFormattedString;
import net.entropysoft.transmorph.converters.ObjectToString;
import net.entropysoft.transmorph.converters.collections.CollectionToString;
import net.entropysoft.transmorph.converters.collections.MapToString;
import net.entropysoft.transmorph.converters.propertyeditors.ToStringUsingPropertyEditor;

/**
 * Default converters when you only want to convert to {@link String}
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class DefaultToStringConverters extends MultiConverter {

	private CollectionToString collectionToString = new CollectionToString();
	private MapToString mapToString = new MapToString();
	private ToStringUsingPropertyEditor toStringUsingPropertyEditor = new ToStringUsingPropertyEditor();
	private CharacterArrayToString characterArrayToString = new CharacterArrayToString();
	private ClassToString classToString = new ClassToString();
	private ObjectToString objectToString = new ObjectToString();
	private ObjectToFormattedString dateToFormattedString = new ObjectToFormattedString(
			Date.class, DateFormat.getInstance());
	private ObjectToFormattedString numberToFormattedString = new ObjectToFormattedString(
			Number.class, NumberFormat.getInstance());
	private MultiConverter multiConverter = new MultiConverter(false);

	public DefaultToStringConverters() {
		this(false, false);
	}

	public DefaultToStringConverters(boolean formatNumber, boolean formatDate) {
		super(false, new IConverter[0]);
		super.addConverter(multiConverter);
		super.addConverter(objectToString);

		addConverter(toStringUsingPropertyEditor);
		addConverter(characterArrayToString);
		addConverter(classToString);
		addConverter(collectionToString);
		addConverter(mapToString);
		if (formatNumber) {
			addConverter(numberToFormattedString);
		}
		if (formatDate) {
			addConverter(dateToFormattedString);
		}
		addConverter(objectToString);
	}

	/**
	 * Add converter before {@link ObjectToString} converter
	 */
	public void addConverter(IConverter converter) {
		multiConverter.addConverter(converter);
	}

	public void removeConverter(IConverter converter) {
		if (converter == objectToString) {
			super.removeConverter(converter);
		} else {
			multiConverter.removeConverter(converter);
		}
	}
	
	public CollectionToString getCollectionToString() {
		return collectionToString;
	}

	public MapToString getMapToString() {
		return mapToString;
	}

	public ToStringUsingPropertyEditor getToStringUsingPropertyEditor() {
		return toStringUsingPropertyEditor;
	}

	public CharacterArrayToString getCharacterArrayToString() {
		return characterArrayToString;
	}

	public ClassToString getClassToString() {
		return classToString;
	}

	public ObjectToString getObjectToString() {
		return objectToString;
	}

	public ObjectToFormattedString getDateToFormattedString() {
		return dateToFormattedString;
	}

	public ObjectToFormattedString getNumberToFormattedString() {
		return numberToFormattedString;
	}

}
