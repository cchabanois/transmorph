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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { StringToURITest.class, IdentityConverterTest.class,
		StringToDateTest.class, StringToURLTest.class, StringToQNameTest.class,
		ObjectToStringTest.class, WrapperToPrimitiveTest.class,
		StringToBooleanTest.class, URIToURLTest.class,
		CalendarToDateTest.class, ObjectToFormattedStringTest.class,
		ImmutableIdentityConverterTest.class, StringToInputStreamTest.class,
		NumberToNumberTest.class, StringToNumberTest.class,
		StringToStringBufferTest.class, MultiStepConverterTest.class,
		FormattedStringToNumberTest.class, CharacterToStringTest.class,
		StringToClassTest.class, StringToCharacterArrayTest.class,
		CharacterArrayToStringTest.class, SingleElementToArrayTest.class,
		StringToStringBuilderTest.class, ClassToStringTest.class,
		SingleElementToCollectionTest.class, StringToTimeZoneTest.class,
		StringToCalendarTest.class, DateToCalendarTest.class,
		StringToFileTest.class, ObjectToObjectUsingConstructorTest.class,
		URLToURITest.class,
		net.entropysoft.transmorph.converters.beans.AllTests.class,
		net.entropysoft.transmorph.converters.collections.AllTests.class,
		net.entropysoft.transmorph.converters.enums.AllTests.class,
		net.entropysoft.transmorph.converters.propertyeditors.AllTests.class })
public class AllTests {

}
