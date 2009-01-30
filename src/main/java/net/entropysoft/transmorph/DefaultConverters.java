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

import net.entropysoft.transmorph.converters.CalendarToDate;
import net.entropysoft.transmorph.converters.CharacterArrayToString;
import net.entropysoft.transmorph.converters.ClassToString;
import net.entropysoft.transmorph.converters.DateToCalendar;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.ImmutableIdentityConverter;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.NumberToNumber;
import net.entropysoft.transmorph.converters.ObjectToObjectUsingConstructor;
import net.entropysoft.transmorph.converters.ObjectToString;
import net.entropysoft.transmorph.converters.StringToBoolean;
import net.entropysoft.transmorph.converters.StringToCalendar;
import net.entropysoft.transmorph.converters.StringToCharacterArray;
import net.entropysoft.transmorph.converters.StringToClass;
import net.entropysoft.transmorph.converters.StringToDate;
import net.entropysoft.transmorph.converters.StringToFile;
import net.entropysoft.transmorph.converters.StringToNumber;
import net.entropysoft.transmorph.converters.StringToQName;
import net.entropysoft.transmorph.converters.StringToStringBuffer;
import net.entropysoft.transmorph.converters.StringToStringBuilder;
import net.entropysoft.transmorph.converters.StringToTimeZone;
import net.entropysoft.transmorph.converters.StringToURI;
import net.entropysoft.transmorph.converters.StringToURL;
import net.entropysoft.transmorph.converters.URIToURL;
import net.entropysoft.transmorph.converters.URLToURI;
import net.entropysoft.transmorph.converters.WrapperToPrimitive;
import net.entropysoft.transmorph.converters.beans.BeanToBean;
import net.entropysoft.transmorph.converters.beans.MapToBean;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;
import net.entropysoft.transmorph.converters.collections.ArrayToCollection;
import net.entropysoft.transmorph.converters.collections.CollectionToArray;
import net.entropysoft.transmorph.converters.collections.CollectionToCollection;
import net.entropysoft.transmorph.converters.collections.MapToMap;
import net.entropysoft.transmorph.converters.enums.EnumToEnum;
import net.entropysoft.transmorph.converters.enums.StringToEnum;

/**
 * The default converters
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class DefaultConverters extends MultiConverter {

	private ImmutableIdentityConverter immutableIdentityConverter = new ImmutableIdentityConverter();
	private NumberToNumber numberToNumber = new NumberToNumber();
	private StringToNumber stringToNumber = new StringToNumber();
	private StringToBoolean stringToBoolean = new StringToBoolean();
	private StringToEnum stringToEnum = new StringToEnum();
	private StringToClass stringToClass = new StringToClass();
	private ClassToString classToString = new ClassToString();
	private StringToStringBuffer stringToStringBuffer = new StringToStringBuffer();
	private StringToStringBuilder stringToStringBuilder = new StringToStringBuilder();
	private StringToCalendar stringToCalendar = new StringToCalendar();
	private ArrayToArray arrayToArray = new ArrayToArray();
	private MapToMap mapToMap = new MapToMap();
	private ArrayToCollection arrayToCollection = new ArrayToCollection();
	private CollectionToCollection collectionToCollection = new CollectionToCollection();
	private CollectionToArray collectionToArray = new CollectionToArray();
	private ObjectToString objectToString = new ObjectToString();
	private DateToCalendar dateToCalendar = new DateToCalendar();
	private CalendarToDate calendarToDate = new CalendarToDate();
	private CharacterArrayToString characterArrayToString = new CharacterArrayToString();
	private IdentityConverter identityConverter = new IdentityConverter();
	private StringToCharacterArray stringToCharacterArray = new StringToCharacterArray();
	private StringToDate stringToDate = new StringToDate();
	private StringToFile stringToFile = new StringToFile();
	private StringToQName stringToQName = new StringToQName();
	private StringToTimeZone stringToTimeZone = new StringToTimeZone();
	private StringToURI stringToURI = new StringToURI();
	private StringToURL stringToURL = new StringToURL();
	private URIToURL uriToUrl = new URIToURL();
	private URLToURI urlToUri = new URLToURI();
	private WrapperToPrimitive wrapperToPrimitive = new WrapperToPrimitive();
	private BeanToBean beanToBean = new BeanToBean();
	private MapToBean mapToBean = new MapToBean();
	private EnumToEnum enumToEnum = new EnumToEnum();
	private ObjectToObjectUsingConstructor objectToObjectUsingConstructor = new ObjectToObjectUsingConstructor();

	private IConverter[] converters = {
			new MultiConverter(immutableIdentityConverter, wrapperToPrimitive,
					numberToNumber, stringToNumber, stringToBoolean,
					stringToEnum, stringToClass, new MultiConverter(
							classToString, characterArrayToString,
							objectToString), enumToEnum, stringToStringBuffer,
					stringToStringBuilder, arrayToArray, mapToMap,
					arrayToCollection, collectionToCollection,
					collectionToArray, dateToCalendar, calendarToDate,
					stringToCalendar, stringToCharacterArray, stringToDate,
					stringToFile, stringToQName, stringToTimeZone, stringToURI,
					stringToURL, uriToUrl, urlToUri, beanToBean, mapToBean,
					objectToObjectUsingConstructor), identityConverter };

	public DefaultConverters() {
		super(new IConverter[0]);
		for (IConverter converter : converters) {
			addConverter(converter);
		}
	}

	public NumberToNumber getNumberToNumber() {
		return numberToNumber;
	}

	public StringToNumber getStringToNumber() {
		return stringToNumber;
	}

	public StringToBoolean getStringToBoolean() {
		return stringToBoolean;
	}

	public StringToEnum getStringToEnum() {
		return stringToEnum;
	}

	public StringToClass getStringToClass() {
		return stringToClass;
	}

	public ClassToString getClassToString() {
		return classToString;
	}

	public StringToStringBuffer getStringToStringBuffer() {
		return stringToStringBuffer;
	}

	public StringToStringBuilder getStringToStringBuilder() {
		return stringToStringBuilder;
	}

	public ArrayToArray getArrayToArray() {
		return arrayToArray;
	}

	public MapToMap getMapToMap() {
		return mapToMap;
	}

	public ArrayToCollection getArrayToCollection() {
		return arrayToCollection;
	}

	public CollectionToCollection getCollectionToCollection() {
		return collectionToCollection;
	}

	public CollectionToArray getCollectionToArray() {
		return collectionToArray;
	}

	public ObjectToString getObjectToString() {
		return objectToString;
	}

	public DateToCalendar getDateToCalendar() {
		return dateToCalendar;
	}

	public CalendarToDate getCalendarToDate() {
		return calendarToDate;
	}

	public IdentityConverter getIdentityConverter() {
		return identityConverter;
	}

	public ImmutableIdentityConverter getImmutableIdentityConverter() {
		return immutableIdentityConverter;
	}

	public StringToCalendar getStringToCalendar() {
		return stringToCalendar;
	}

	public CharacterArrayToString getCharacterArrayToString() {
		return characterArrayToString;
	}

	public StringToCharacterArray getStringToCharacterArray() {
		return stringToCharacterArray;
	}

	public StringToDate getStringToDate() {
		return stringToDate;
	}

	public StringToFile getStringToFile() {
		return stringToFile;
	}

	public StringToQName getStringToQName() {
		return stringToQName;
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

	public URIToURL getUriToUrl() {
		return uriToUrl;
	}

	public URLToURI getUrlToUri() {
		return urlToUri;
	}

	public WrapperToPrimitive getWrapperToPrimitive() {
		return wrapperToPrimitive;
	}

	public BeanToBean getBeanToBean() {
		return beanToBean;
	}

	public MapToBean getMapToBean() {
		return mapToBean;
	}

	public EnumToEnum getEnumToEnum() {
		return enumToEnum;
	}

	public ObjectToObjectUsingConstructor getObjectToObjectUsingConstructor() {
		return objectToObjectUsingConstructor;
	}

//	public IConverter[] getConverters() {
//		return converters;
//	}

}
