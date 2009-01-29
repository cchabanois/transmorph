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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.converters.TestConverters;

public class CollectionToArrayTest extends TestCase {

	public void testListToArray() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		List<Integer> source = new ArrayList<Integer>();
		source.add(30);
		source.add(40);
		source.add(50);

		String[] array = (String[]) converter.convert(source, String[].class);
		assertNotNull(array);
		assertEquals(3, array.length);
		assertEquals("30", array[0]);
		assertEquals("40", array[1]);
		assertEquals("50", array[2]);
	}
	
	public void testListOfListToArray2D() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		
		List<List<Integer>> listListInteger = new ArrayList<List<Integer>>();
		List<Integer> listInteger = new ArrayList<Integer>();
		listInteger.add(11);
		listInteger.add(12);
		listInteger.add(13);
		listListInteger.add(listInteger);

		listInteger = new ArrayList<Integer>();
		listInteger.add(21);
		listInteger.add(22);
		listInteger.add(23);
		listListInteger.add(listInteger);

		String[][] array2D = (String[][]) converter.convert(listListInteger,
				String[][].class);
		assertEquals("11", array2D[0][0]);
		assertEquals("12", array2D[0][1]);
		assertEquals("13", array2D[0][2]);
		assertEquals("21", array2D[1][0]);
		assertEquals("22", array2D[1][1]);
		assertEquals("23", array2D[1][2]);
	}
	
	
}