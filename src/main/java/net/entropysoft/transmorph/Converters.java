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
import net.entropysoft.transmorph.converters.ClassToString;
import net.entropysoft.transmorph.converters.DateToCalendar;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.NumberToNumber;
import net.entropysoft.transmorph.converters.ObjectToString;
import net.entropysoft.transmorph.converters.StringToBoolean;
import net.entropysoft.transmorph.converters.StringToClass;
import net.entropysoft.transmorph.converters.StringToNumber;
import net.entropysoft.transmorph.converters.StringToStringBuffer;
import net.entropysoft.transmorph.converters.StringToStringBuilder;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;
import net.entropysoft.transmorph.converters.collections.ArrayToCollection;
import net.entropysoft.transmorph.converters.collections.CollectionToArray;
import net.entropysoft.transmorph.converters.collections.CollectionToCollection;
import net.entropysoft.transmorph.converters.collections.MapToMap;
import net.entropysoft.transmorph.converters.enums.StringToEnum;

public class Converters {


	public static IConverter[] getDefaultConverters() {
		return new IConverter[] { new NumberToNumber(), new StringToNumber(), new StringToBoolean(), new StringToEnum(),
				new StringToClass(), new ClassToString(), new StringToStringBuffer(), new StringToStringBuilder(),
				new ArrayToArray(), new MapToMap(), new ArrayToCollection(), new CollectionToCollection(),
				new CollectionToArray(), new ObjectToString(), new DateToCalendar(), new CalendarToDate(),
				new IdentityConverter() };
	};
	
}
