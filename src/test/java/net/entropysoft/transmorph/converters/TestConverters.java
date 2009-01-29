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

import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.enums.EnumToEnum;
import net.entropysoft.transmorph.converters.enums.StringToEnum;

public class TestConverters {

	public final static IConverter[] converters = new IConverter[] {
			new ImmutableIdentityConverter(), new WrapperToPrimitive(),
			new NumberToNumber(), new StringToNumber(), new StringToBoolean(),
			new StringToEnum(), new EnumToEnum(), new StringToStringBuffer(),
			new StringToStringBuilder(), new StringToClass(),
			new ClassToString(), new ArrayToArray(), new MapToMap(),
			new ArrayToCollection(), new CollectionToCollection(),
			new CollectionToArray(), new StringToFile(), new StringToURL(),
			new StringToURI(), new URIToURL(), new URLToURI(),
			new CharacterArrayToString(), new StringToCharacterArray(),
			new StringToQName(), new StringToTimeZone(), new ObjectToString(),
			new DateToCalendar(), new CalendarToDate(), new IdentityConverter() };

}
