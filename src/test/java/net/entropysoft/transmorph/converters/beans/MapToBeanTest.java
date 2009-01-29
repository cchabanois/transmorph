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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.type.TypeFactory;
import samples.MyBean1;
import samples.MyBean2;
import samples.MyBean3;

public class MapToBeanTest extends TestCase {

	public void testMapToBean() throws Exception {
		TypeFactory typeFactory = new TypeFactory(ConverterTest.class
				.getClassLoader());

		DefaultConverters defaultConverters = new DefaultConverters();
		
		BeanPropertyTypeProvider beanDestinationPropertyTypeProvider = new BeanPropertyTypeProvider();
		beanDestinationPropertyTypeProvider.setPropertyDestinationType(
				MyBean3.class, "myList", typeFactory.getType(List.class,
						new Class[] { String.class }));
		defaultConverters.getMapToBean()
				.setBeanPropertyTypeProvider(beanDestinationPropertyTypeProvider);

		Transmorph converter = new Transmorph(typeFactory, defaultConverters);
		Map<String, Object> mapBean1 = new HashMap<String, Object>();
		mapBean1.put("myInt", "15");
		mapBean1.put("myListOfStrings", new int[] { 1, 2, 3 });

		Map<String, Object> mapBean2 = new HashMap<String, Object>();
		mapBean2.put("myString", new Float(55.2));
		mapBean1.put("myBean2", mapBean2);

		Map<String, Object> mapBean3 = new HashMap<String, Object>();
		mapBean3.put("myList", new int[] { 1, 2, 3 });
		mapBean1.put("myBean3", mapBean3);

		MyBean1 myBean1 = (MyBean1) converter.convert(mapBean1, MyBean1.class);
		assertNotNull(myBean1);
		assertEquals(15, myBean1.getMyInt());
		List<String> listOfStrings = myBean1.getMyListOfStrings();
		assertEquals("1", listOfStrings.get(0));
		assertEquals("2", listOfStrings.get(1));
		assertEquals("3", listOfStrings.get(2));
		MyBean2 myBean2 = myBean1.getMyBean2();
		assertEquals("55.2", myBean2.getMyString());
		MyBean3 myBean3 = myBean1.getMyBean3();
		listOfStrings = myBean3.getMyList();
		assertEquals("1", listOfStrings.get(0));
		assertEquals("2", listOfStrings.get(1));
		assertEquals("3", listOfStrings.get(2));
	}
	
	
}
