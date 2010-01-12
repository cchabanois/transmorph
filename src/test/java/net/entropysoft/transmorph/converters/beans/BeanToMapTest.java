/*
 * Copyright 2008-2010 the original author or authors.
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
package net.entropysoft.transmorph.converters.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.ObjectToFormattedString;
import net.entropysoft.transmorph.converters.SubTypeConverter;
import net.entropysoft.transmorph.converters.collections.ArrayToCollection;
import net.entropysoft.transmorph.converters.collections.CollectionToCollection;
import net.entropysoft.transmorph.converters.collections.MapToMap;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

import samples.MyBean4;

public class BeanToMapTest {

	@Test
	public void testBeanToMap() throws Exception {
		Transmorph converter = new Transmorph(new BeanToMap(),
				new IdentityConverter());

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("my string");
		myBean4.setMyStrings(Arrays.asList("first", "second", "third"));
		myBean4.setSize(42);

		Map<String, Object> map = converter.convert(myBean4,
				new TypeReference<Map<String, Object>>() {
				});
		assertNotNull(map);
		assertEquals(3, map.size());
		assertEquals(42, map.get("size"));
		assertEquals("my string", map.get("myString"));
		List<String> listOfStrings = (List<String>) map.get("myStrings");
		assertEquals("[first, second, third]", listOfStrings.toString());
	}

	@Test
	public void testBeanToMapOfSomeObjectsOnly() throws Exception {
		// we want to convert a bean to a Map that can contain only Map, List or
		// String (which also only contain Map, List, String ...)
		BeanToMap beanToMap = new BeanToMap();
		SubTypeConverter subTypeConverter = new SubTypeConverter(TypeReference
				.get(Object.class), new TypeReference[] {
				TypeReference.get(List.class), TypeReference.get(Map.class),
				TypeReference.get(String.class) });
		beanToMap.setElementConverter(subTypeConverter);

		MultiConverter toListConverter = new MultiConverter(
				new CollectionToCollection(), new ArrayToCollection());
		toListConverter.setElementConverter(subTypeConverter);
		subTypeConverter.setConverter(TypeReference.get(List.class),
				toListConverter);

		MultiConverter toMapConverter = new MultiConverter(new MapToMap(),
				beanToMap);
		toMapConverter.setElementConverter(subTypeConverter);
		subTypeConverter.setConverter(TypeReference.get(Map.class),
				toMapConverter);

		MultiConverter toStringConverter = new MultiConverter(
				new ObjectToFormattedString(Number.class, NumberFormat
						.getInstance()), new IdentityConverter());
		toStringConverter.setElementConverter(subTypeConverter);
		subTypeConverter.setConverter(TypeReference.get(String.class),
				toStringConverter);

		Transmorph converter = new Transmorph(beanToMap);

		MyBean1 myBean1 = new MyBean1();
		myBean1.setMyInt(55);
		myBean1.setMyInts(new int[] { 1, 2, 3 });
		MyBean2 myBean2 = new MyBean2();
		myBean2.setMyLong(112312);
		myBean2
				.setMySetOfIntegers(new HashSet<Integer>(Arrays.asList(1, 2, 3)));

		myBean1.setMyBean2(myBean2);

		Map<String, Object> myBean1AsMap = converter.convert(myBean1,
				new TypeReference<Map<String, Object>>() {
				});
		assertNotNull(myBean1AsMap);
		assertEquals("55", myBean1AsMap.get("myInt"));
		assertTrue(myBean1AsMap.get("myInts") instanceof List);
		List<String> myIntsAsStrings = (List<String>) myBean1AsMap
				.get("myInts");
		assertEquals("1", myIntsAsStrings.get(0));
		assertEquals("2", myIntsAsStrings.get(1));
		assertEquals("3", myIntsAsStrings.get(2));

		assertTrue(myBean1AsMap.get("myBean2") instanceof Map);
		Map<String, Object> myBean2AsMap = (Map<String, Object>) myBean1AsMap
				.get("myBean2");
		assertEquals("112 312", myBean2AsMap.get("myLong"));
		assertTrue(myBean2AsMap.get("mySetOfIntegers") instanceof List);
		List<String> mySetOfIntegersAsStrings = (List<String>) myBean2AsMap
				.get("mySetOfIntegers");
		assertTrue(mySetOfIntegersAsStrings.contains("1"));
		assertTrue(mySetOfIntegersAsStrings.contains("2"));
		assertTrue(mySetOfIntegersAsStrings.contains("3"));
	}

	public static class MyBean1 {
		private int[] myInts;
		private int myInt;
		private MyBean2 myBean2;

		public MyBean1() {
		}

		public int[] getMyInts() {
			return myInts;
		}

		public void setMyInts(int[] myInts) {
			this.myInts = myInts;
		}

		public int getMyInt() {
			return myInt;
		}

		public void setMyInt(int myInt) {
			this.myInt = myInt;
		}

		public void setMyBean2(MyBean2 myBean2) {
			this.myBean2 = myBean2;
		}

		public MyBean2 getMyBean2() {
			return myBean2;
		}
	}

	public static class MyBean2 {
		private long myLong;
		private Set<Integer> mySetOfIntegers;

		public MyBean2() {
		}

		public void setMyLong(long myLong) {
			this.myLong = myLong;
		}

		public long getMyLong() {
			return myLong;
		}

		public Set<Integer> getMySetOfIntegers() {
			return mySetOfIntegers;
		}

		public void setMySetOfIntegers(Set<Integer> mySetOfIntegers) {
			this.mySetOfIntegers = mySetOfIntegers;
		}

	}

}
