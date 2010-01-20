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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class CollectionToArrayTest {

	@Test
	public void testListToArray() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

		List<Integer> source = new ArrayList<Integer>();
		source.add(30);
		source.add(40);
		source.add(50);

		String[] array = converter.convert(source, String[].class);
		assertNotNull(array);
		assertEquals(3, array.length);
		assertEquals("30", array[0]);
		assertEquals("40", array[1]);
		assertEquals("50", array[2]);
	}

	@Test
	public void testListOfListToArray2D() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

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

		String[][] array2D = converter.convert(listListInteger,
				String[][].class);
		assertEquals("11", array2D[0][0]);
		assertEquals("12", array2D[0][1]);
		assertEquals("13", array2D[0][2]);
		assertEquals("21", array2D[1][0]);
		assertEquals("22", array2D[1][1]);
		assertEquals("23", array2D[1][2]);
	}

	@Test
	public void testListNotParameterizedToArray() throws Exception {
		Transmorph transmorph = new Transmorph(new DefaultConverters());
		List list = new ArrayList();
		list.add(new Integer(1));
		list.add(new Integer(2));
		list.add(new Integer(3));

		String[] arrayOfStrings = (String[]) transmorph.convert(list, String[].class);
	}

	@Test
	public void test4DArrayListTo4DArrayString() throws Exception {
		// see http://stackoverflow.com/questions/1922530/how-to-convert-nested-list-into-multidimensional-array
		ArrayList<ArrayList<ArrayList<ArrayList<String>>>> arrayList = new ArrayList<ArrayList<ArrayList<ArrayList<String>>>>();
		
		for (int i = 0; i < 10; i++) {
			ArrayList<ArrayList<ArrayList<String>>> arrayListI = new ArrayList<ArrayList<ArrayList<String>>>();
			arrayList.add(arrayListI);
			for (int j = 0; j < 5; j++) {
				ArrayList<ArrayList<String>> arrayListJ = new ArrayList<ArrayList<String>>();
				arrayListI.add(arrayListJ);
				for (int k = 0; k < 5; k++) {
					ArrayList<String> arrayListK = new ArrayList<String>();
					arrayListJ.add(arrayListK);
					for (int l = 0; l < 20; l++) {
						arrayListK.add("i="+i+",j="+j+",k="+k+",l="+l);
					}	
				}
			}
		}
		Transmorph transmorph = new Transmorph(new DefaultConverters());
		String[][][][] array = transmorph.convert(arrayList, String[][][][].class);
	}
	
}
