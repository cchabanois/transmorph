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

import junit.framework.TestCase;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;

public class StringToBooleanTest extends TestCase {

	public void testStringToBooleanPrimitive() throws Exception {
		Converter converter = new Converter(this.getClass().getClassLoader(),
				TestConverters.converters);

		// String => boolean (StringToBooleanConverter)
		boolean myBoolean = (Boolean) converter.convert("false", Boolean.TYPE);
		assertEquals(false, myBoolean);
		myBoolean = (Boolean) converter.convert("true", Boolean.TYPE);
		assertEquals(true, myBoolean);

		try {
			myBoolean = (Boolean) converter.convert(null, Boolean.TYPE);
			fail("Should not have been converted");
		} catch (ConverterException e) {
		}
	}

	public void testOtherStringToBooleanPrimitive() throws Exception {
		StringToBoolean stringToBoolean = new StringToBoolean();
		Converter converter = new Converter(this.getClass().getClassLoader(),
				new IConverter[] { stringToBoolean, new IdentityConverter() });

		boolean myBoolean;
		try {
			myBoolean = (Boolean) converter.convert("faux", Boolean.TYPE);
			fail("Should not have been converted");
		} catch (ConverterException e) {
		}
		stringToBoolean.setCaseSensitive(false);
		stringToBoolean.setTrueString("vrai");
		stringToBoolean.setFalseString("faux");

		myBoolean = (Boolean) converter.convert("Faux", Boolean.TYPE);
		assertEquals(false, myBoolean);

		myBoolean = (Boolean) converter.convert("Vrai", Boolean.TYPE);
		assertEquals(true, myBoolean);
	}

	public void testBooleanToBooleanWrapper() throws Exception {
		Converter converter = new Converter(this.getClass().getClassLoader(),
				TestConverters.converters);
		Boolean booleanObject = (Boolean) converter
				.convert(null, Boolean.class);
		assertNull(booleanObject);
	}

}