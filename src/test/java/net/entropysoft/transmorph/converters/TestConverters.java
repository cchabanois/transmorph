package net.entropysoft.transmorph.converters;

import net.entropysoft.transmorph.IConverter;

public class TestConverters {

	public final static IConverter[] converters = new IConverter[] {
			new WrapperToPrimitive(), new NumberToNumber(),
			new StringToNumber(), new StringToBoolean(), new StringToEnum(),
			new StringToStringBuffer(), new StringToStringBuilder(),
			new StringToClass(), new ClassToString(), new ArrayToArray(),
			new MapToMap(), new ArrayToCollection(),
			new CollectionToCollection(), new CollectionToArray(),
			new StringToFile(), new StringToURL(), new StringToURI(),
			new URIToURL(), new URLToURI(), new CharacterArrayToString(),
			new StringToCharacterArray(), new StringToQName(),
			new StringToTimeZone(), new ObjectToString(), new DateToCalendar(),
			new CalendarToDate(), new IdentityConverter() };

}
