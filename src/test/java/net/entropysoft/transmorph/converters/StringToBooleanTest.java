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
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

public class StringToBooleanTest extends TestCase {

	public void testStringToBooleanPrimitive() throws Exception {
		Transmorph converter = new Transmorph(this.getClass().getClassLoader(),
				new DefaultConverters());

		// String => boolean (StringToBooleanConverter)
		boolean myBoolean = converter.convert("false", Boolean.TYPE);
		assertEquals(false, myBoolean);
		myBoolean = converter.convert("true", Boolean.TYPE);
		assertEquals(true, myBoolean);

		try {
			myBoolean = converter.convert(null, Boolean.TYPE);
			fail("Should not have been converted");
		} catch (ConverterException e) {
		}
	}

	public void testOtherStringToBooleanPrimitive() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		StringToBoolean stringToBoolean = defaultConverters
				.getStringToBoolean();
		Transmorph converter = new Transmorph(this.getClass().getClassLoader(),
				defaultConverters);

		boolean myBoolean;
		try {
			myBoolean = converter.convert("faux", Boolean.TYPE);
			fail("Should not have been converted");
		} catch (ConverterException e) {
		}
		stringToBoolean.setCaseSensitive(false);
		stringToBoolean.setTrueString("vrai");
		stringToBoolean.setFalseString("faux");

		myBoolean = converter.convert("Faux", Boolean.TYPE);
		assertEquals(false, myBoolean);

		myBoolean = converter.convert("Vrai", Boolean.TYPE);
		assertEquals(true, myBoolean);
	}

	public void testBooleanToBooleanWrapper() throws Exception {
		Transmorph converter = new Transmorph(this.getClass().getClassLoader(),
				new DefaultConverters());
		Boolean booleanObject = converter.convert(null, Boolean.class);
		assertNull(booleanObject);
	}

}
