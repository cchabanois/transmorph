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
package net.entropysoft.transmorph.converters.beans;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.type.TypeReference;

public class MapWithClassToBeanMappingTest {

	@Test
	public void testMapWithClassToBeanMapping() throws Exception {
		Transmorph converter = createConverter();

		MyFirstImplementation myFirstImplementation = new MyFirstImplementation();
		myFirstImplementation.setMyString("This is my string");

		MySecondImplementation mySecondImplementation = new MySecondImplementation();
		mySecondImplementation.setMyInt(22);

		Map<String, String> mapOfStrings = converter.convert(
				myFirstImplementation,
				new TypeReference<Map<String, String>>() {
				});

		IMyInterface myInterface = converter.convert(mapOfStrings,
				IMyInterface.class);
		assertEquals("This is my string", myInterface.getMyString());

		mapOfStrings = converter.convert(mySecondImplementation,
				new TypeReference<Map<String, String>>() {
				});
		myInterface = converter.convert(mapOfStrings, IMyInterface.class);
		assertEquals("22", myInterface.getMyString());
		myInterface = converter.convert(mapOfStrings, MySecondImplementation.class);
		assertEquals("22", myInterface.getMyString());
	}

	private Transmorph createConverter() {
		DefaultConverters defaultConverters = new DefaultConverters();
		defaultConverters.getMapToBean().setMapToBeanMapping(
				new MapWithClassToBeanMapping(getClass().getClassLoader()));
		BeanToMap beanToMap = new BeanToMap();
		DefaultBeanToMapMapping beanToMapMapping = new DefaultBeanToMapMapping();
		beanToMapMapping.setKeepClass(true);
		beanToMap.setBeanToMapMapping(beanToMapMapping);
		defaultConverters.addConverter(beanToMap);
		Transmorph converter = new Transmorph(defaultConverters);
		return converter;
	}
	
	public static interface IMyInterface {
		public String getMyString();
	}

	public static class MyFirstImplementation implements IMyInterface {
		private String myString;

		public String getMyString() {
			return myString;
		}

		public void setMyString(String myString) {
			this.myString = myString;
		}
	}

	public static class MySecondImplementation implements IMyInterface {
		private int myInt;

		public String getMyString() {
			return Integer.toString(myInt);
		}

		public int getMyInt() {
			return myInt;
		}
		
		public void setMyInt(int myInt) {
			this.myInt = myInt;
		}
	}

}
