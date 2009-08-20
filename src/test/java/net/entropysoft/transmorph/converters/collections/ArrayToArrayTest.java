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
package net.entropysoft.transmorph.converters.collections;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

public class ArrayToArrayTest extends TestCase {

	public void testObjectArrayToStringArray() throws Exception {
		Transmorph converter = new Transmorph(ArrayToArrayTest.class
				.getClassLoader(), new DefaultConverters());

		// Object[] => String[] (ArrayToArrayConverter)
		Object[] arrayOfObjects = new Object[] { "one", "two", "three" };
		String[] arrayOfStrings = converter.convert(arrayOfObjects,
				(new String[0]).getClass());
		assertEquals("one", arrayOfStrings[0]);
		assertEquals("two", arrayOfStrings[1]);
		assertEquals("three", arrayOfStrings[2]);
	}

	public void testMultidimentionalArray() throws Exception {
		Transmorph converter = new Transmorph(ArrayToArrayTest.class
				.getClassLoader(), new DefaultConverters());

		// int[][] => String[][] (ArrayToArrayConverter)
		int[][] arrayOfArrayOfInts = new int[][] { { 11, 12, 13 },
				{ 21, 22, 23 }, { 31 } };
		String[][] arrayOfArrayOfStrings = converter.convert(
				arrayOfArrayOfInts, (new String[0][0]).getClass());
		for (int i = 0; i < arrayOfArrayOfInts.length; i++) {
			for (int j = 0; j < arrayOfArrayOfInts[i].length; j++) {
				assertEquals(Integer.toString(arrayOfArrayOfInts[i][j]),
						arrayOfArrayOfStrings[i][j]);
			}
		}

		// int[] => String[][] (ArrayToArrayConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		try {
			arrayOfArrayOfStrings = converter.convert(arrayOfInts,
					(new String[0][0]).getClass());
			fail("Should not have been able to convert");
		} catch (ConverterException e) {

		}
	}

}
