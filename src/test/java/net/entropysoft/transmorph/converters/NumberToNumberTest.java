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

import java.math.BigInteger;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;

public class NumberToNumberTest extends TestCase {

	public void testNumberPrimitiveToNumberPrimitive() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());

		// int => long (NumberToNumberConverter)
		int myInt = 55;
		long myLong = (Long) converter.convert(myInt, Long.TYPE);
		assertEquals(55, myLong);

		try {
			Long myLong2 = (Long) converter.convert(null, Long.TYPE);
			fail("Should not have been able to convert");
		} catch (ConverterException e) {

		}
		assertEquals((byte) -126, converter.convert(130, Byte.TYPE));
	}

	public void testNumberWrapperToNumberWrapper() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());

		Long myLongWrapper = (Long) converter.convert(new Integer(44),
				Long.class);
		assertEquals(Long.valueOf(44), myLongWrapper);

		myLongWrapper = (Long) converter.convert(null, Long.class);
		assertEquals(null, myLongWrapper);
	}

	public void testNumberPrimitiveToNumberWrapper() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());

		// int => Long (NumberToNumberConverter)
		assertEquals(new Long(55), converter.convert(55, Long.class));

		// int => BigInteger (NumberToNumberConverter)
		assertEquals(BigInteger.valueOf(55), (BigInteger) converter.convert(55,
				BigInteger.class));
	}

	public void testNullReplacementForPrimitive() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		NumberToNumber numberToNumber = defaultConverters.getNumberToNumber();
		numberToNumber.setNullReplacementForPrimitive(0);
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), defaultConverters);
		long[] longsArray = (long[]) converter.convert(new Integer[] { 1, 2,
				null, 4 }, long[].class);
		assertEquals(1, longsArray[0]);
		assertEquals(2, longsArray[1]);
		assertEquals(0, longsArray[2]);
		assertEquals(4, longsArray[3]);
	}

}
