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

import java.math.BigDecimal;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

public class StringToNumberTest extends TestCase {

	public void testStringToPrimitive() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

		// String => int (StringToNumberConverter)
		String myStr = "50";
		int myInt = converter.convert(myStr, Integer.TYPE);
		assertEquals(50, myInt);

		try {
			myStr = "129";
			// Value out of range
			byte myByte = converter.convert(myStr, Byte.TYPE);
			assertEquals(50, myInt);
			fail("Should not be able to convert");
		} catch (ConverterException e) {

		}
	}

	public void testStringToBigDecimal() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

		// String => BigDecimal (StringToNumberConverter)
		BigDecimal bigDecimal = converter
				.convert("5.56564546546464646577775612321443244664456",
						BigDecimal.class);
		assertEquals("5.56564546546464646577775612321443244664456", bigDecimal
				.toString());
	}

	public void testStringToNumber() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

		Number number = converter.convert(
				"5.56564546546464646577775612321443244664456", Number.class);
		assertNotNull(number);
		assertTrue(number instanceof Double);
	}

}
