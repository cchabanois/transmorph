package net.entropysoft.transmorph;

import net.entropysoft.transmorph.converters.ArrayToArray;
import net.entropysoft.transmorph.converters.ArrayToCollection;
import net.entropysoft.transmorph.converters.CalendarToDate;
import net.entropysoft.transmorph.converters.CharacterArrayToString;
import net.entropysoft.transmorph.converters.CollectionToArray;
import net.entropysoft.transmorph.converters.CollectionToCollection;
import net.entropysoft.transmorph.converters.DateToCalendar;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.MapToBean;
import net.entropysoft.transmorph.converters.MapToMap;
import net.entropysoft.transmorph.converters.NumberToNumber;
import net.entropysoft.transmorph.converters.ObjectToObjectUsingConstructor;
import net.entropysoft.transmorph.converters.ObjectToString;
import net.entropysoft.transmorph.converters.SingleElementToArray;
import net.entropysoft.transmorph.converters.SingleElementToCollection;
import net.entropysoft.transmorph.converters.StringToBoolean;
import net.entropysoft.transmorph.converters.StringToCalendar;
import net.entropysoft.transmorph.converters.StringToCharacterArray;
import net.entropysoft.transmorph.converters.StringToClass;
import net.entropysoft.transmorph.converters.StringToDate;
import net.entropysoft.transmorph.converters.StringToEnum;
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

public class Converters {
	public final static IConverter numberToNumber = new NumberToNumber();
	public final static IConverter stringToNumber = new StringToNumber();
	public final static IConverter stringToBoolean = new StringToBoolean();
	public final static IConverter arrayToArray = new ArrayToArray();
	public final static IConverter mapToMap = new MapToMap();
	public final static IConverter arrayToCollection = new ArrayToCollection();
	public final static IConverter collectionToCollection = new CollectionToCollection();
	public final static IConverter collectionToArray = new CollectionToArray();
	public final static IConverter objectToString = new ObjectToString();
	public final static IConverter stringToEnum = new StringToEnum();
	public final static IConverter stringToClass = new StringToClass();
	public final static IConverter dateToCalendar = new DateToCalendar();
	public final static IConverter calendarToDate = new CalendarToDate();
	public final static IConverter stringToStringBuffer = new StringToStringBuffer();
	public final static IConverter stringToStringBuilder = new StringToStringBuilder();
	public final static IConverter identityConverter = new IdentityConverter();
	
	public final static IConverter[] defaultConverters = new IConverter[] {
			numberToNumber, stringToNumber, stringToBoolean, stringToEnum,
			stringToClass, stringToStringBuffer, stringToStringBuilder,
			arrayToArray, mapToMap, arrayToCollection, collectionToCollection,
			collectionToArray, objectToString, dateToCalendar, calendarToDate,
			identityConverter };

	// not in defaultConverters :
	public final static IConverter stringToFile = new StringToFile();
	public final static IConverter stringToURL = new StringToURL();
	public final static IConverter stringToURI = new StringToURI();
	public final static IConverter stringToQName = new StringToQName();
	public final static IConverter stringToTimeZone = new StringToTimeZone();
	public final static IConverter uriToUrl = new URIToURL();
	public final static IConverter urlToUri = new URLToURI();
	public final static IConverter characterArrayToString = new CharacterArrayToString();
	public final static IConverter stringToCharacterArray = new StringToCharacterArray();
	public final static IConverter objectToObjectUsingConstructor = new ObjectToObjectUsingConstructor();
	public final static IConverter stringToDate = new StringToDate();
	public final static IConverter stringToCalendar = new StringToCalendar();
	public final static IConverter maptoBean = new MapToBean();
	public final static IConverter singleElementToArray = new SingleElementToArray();
	public final static IConverter singleElementToCollection = new SingleElementToCollection();

}
