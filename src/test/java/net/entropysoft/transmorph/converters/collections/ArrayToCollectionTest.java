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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.signature.parser.ClassFileTypeSignatureParser;

public class ArrayToCollectionTest extends TestCase {

	public void testArrayOfPrimitivesToGenericList() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());

		// int[] => List<Integer> (ArrayToCollectionConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		List<Integer> listOfInts = (List<Integer>) converter.convert(
				arrayOfInts, List.class, new Class[] { Integer.class });
		assertEquals(6, listOfInts.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, listOfInts.get(i).intValue());
		}
	}

	public void testArrayOfPrimitivesToListInstance() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		// int[] => LinkedList<Integer> (ArrayToCollectionConverter)
		LinkedList<Integer> linkedList = (LinkedList<Integer>) converter
				.convert(arrayOfInts, LinkedList.class,
						new Class[] { Integer.class });
		assertNotNull(linkedList);
		assertEquals(6, linkedList.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, linkedList.get(i).intValue());
		}

	}

	public void testArrayToGenericListUnbounded() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());
		converter.setTypeSignatureParser(new ClassFileTypeSignatureParser(false));
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		// int[] => List<*> (ArrayToCollectionConverter)
		List<?> arrayOfSomething = (List<?>) converter.convert(arrayOfInts,
				"Ljava.util.List<*>;");
		assertNotNull(arrayOfSomething);
		assertEquals(6, arrayOfSomething.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, arrayOfSomething.get(i));
		}		
	}
	
	public void testArrayToGenericListUpperbound() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());
		converter.setTypeSignatureParser(new ClassFileTypeSignatureParser(false));
		// String[] => List<? extends Number> (ArrayToCollectionConverter)
		String[] arrayOfStrings = new String[] { "0", "1", "2", "3", "4", "5" };
		List<? extends Number> listOfNumbers = (List<? extends Number>) converter
				.convert(arrayOfStrings,
						"Ljava.util.List<+Ljava.lang.Number;>;");
		assertNotNull(listOfNumbers);
		assertEquals(6, listOfNumbers.size());
		for (int i = 0; i < 6; i++) {
			assertEquals(i, listOfNumbers.get(i).intValue());
		}
	}

	public void testArrayToCollection() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());

		// int[] => Collection<Integer> (ArrayToCollectionConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		Collection<Integer> collectionOfInts = (Collection<Integer>) converter
				.convert(arrayOfInts, Collection.class,
						new Class[] { Integer.class });
		assertEquals(6, collectionOfInts.size());
		int j = 0;
		for (int i : collectionOfInts) {
			assertEquals(j, i);
			j++;
		}
	}
	
	public void testArrayToGenericSet() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());

		// int[] => Set<Integer> (ArrayToSetConverter)
		int[] arrayOfInts = new int[] { 0, 1, 2, 3, 4, 5 };
		Set<Integer> setOfInts = (Set<Integer>) converter.convert(arrayOfInts,
				Set.class, new Class[] { Integer.class });
		assertEquals(6, setOfInts.size());
		for (int i = 0; i < 6; i++) {
			assertTrue(setOfInts.contains(i));
		}
	}	
	
}
