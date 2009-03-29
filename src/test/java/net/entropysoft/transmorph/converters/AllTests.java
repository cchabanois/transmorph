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

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for net.entropysoft.transmorph.converters");
		//$JUnit-BEGIN$
		suite.addTestSuite(StringToURITest.class);
		suite.addTestSuite(IdentityConverterTest.class);
		suite.addTestSuite(StringToDateTest.class);
		suite.addTestSuite(StringToURLTest.class);
		suite.addTestSuite(StringToQNameTest.class);
		suite.addTestSuite(ObjectToStringTest.class);
		suite.addTestSuite(WrapperToPrimitiveTest.class);
		suite.addTestSuite(StringToBooleanTest.class);
		suite.addTestSuite(URIToURLTest.class);
		suite.addTestSuite(CalendarToDateTest.class);
		suite.addTestSuite(ObjectToFormattedStringTest.class);
		suite.addTestSuite(ImmutableIdentityConverterTest.class);
		suite.addTestSuite(StringToInputStreamTest.class);
		suite.addTestSuite(NumberToNumberTest.class);
		suite.addTestSuite(StringToNumberTest.class);
		suite.addTestSuite(StringToStringBufferTest.class);
		suite.addTestSuite(MultiStepConverterTest.class);
		suite.addTestSuite(FormattedStringToNumberTest.class);
		suite.addTestSuite(CharacterToStringTest.class);
		suite.addTestSuite(StringToClassTest.class);
		suite.addTestSuite(StringToCharacterArrayTest.class);
		suite.addTestSuite(CharacterArrayToStringTest.class);
		suite.addTestSuite(SingleElementToArrayTest.class);
		suite.addTestSuite(StringToStringBuilderTest.class);
		suite.addTestSuite(ClassToStringTest.class);
		suite.addTestSuite(SingleElementToCollectionTest.class);
		suite.addTestSuite(StringToTimeZoneTest.class);
		suite.addTestSuite(StringToCalendarTest.class);
		suite.addTestSuite(DateToCalendarTest.class);
		suite.addTestSuite(StringToFileTest.class);
		suite.addTestSuite(ObjectToObjectUsingConstructorTest.class);
		suite.addTestSuite(URLToURITest.class);
		//$JUnit-END$

		suite.addTest(net.entropysoft.transmorph.converters.beans.AllTests
				.suite());
		suite
				.addTest(net.entropysoft.transmorph.converters.collections.AllTests
						.suite());
		suite.addTest(net.entropysoft.transmorph.converters.enums.AllTests
				.suite());

		suite
				.addTest(net.entropysoft.transmorph.converters.propertyeditors.AllTests
						.suite());

		return suite;
	}

}
