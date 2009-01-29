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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.CharacterArrayToString;
import net.entropysoft.transmorph.converters.DateToCalendar;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.NumberToNumber;
import net.entropysoft.transmorph.converters.ObjectToString;
import net.entropysoft.transmorph.converters.StringToCharacterArray;
import net.entropysoft.transmorph.converters.StringToClass;
import net.entropysoft.transmorph.converters.StringToFile;
import net.entropysoft.transmorph.converters.StringToNumber;
import net.entropysoft.transmorph.converters.StringToURL;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;
import net.entropysoft.transmorph.converters.collections.ArrayToCollection;
import net.entropysoft.transmorph.converters.collections.CollectionToArray;
import net.entropysoft.transmorph.converters.collections.CollectionToCollection;
import net.entropysoft.transmorph.converters.collections.MapToMap;
import samples.MyBean4;
import samples.MyBean4TransferObject;
import samples.MyBeanAB;
import samples.MyBeanABTransferObject;
import samples.MyBeanBA;
import samples.MyBeanBATransferObject;

public class BeanToBeanTest extends TestCase {

	public void testBiDirectionalBean() throws Exception {
		BeanToBean beanToBean = new BeanToBean();
		IConverter[] converters = new IConverter[] { new NumberToNumber(),
				new StringToNumber(), new StringToClass(), new ArrayToArray(),
				new MapToMap(), new ArrayToCollection(),
				new CollectionToCollection(), new CollectionToArray(),
				new StringToFile(), new StringToURL(),
				new CharacterArrayToString(), new StringToCharacterArray(),
				new ObjectToString(), new DateToCalendar(),
				new IdentityConverter(), beanToBean };
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		BeanToBeanMapping beanToBeanMapping = new BeanToBeanMapping(
				MyBeanAB.class, MyBeanABTransferObject.class);
		beanToBean.addBeanToBeanMapping(beanToBeanMapping);

		beanToBeanMapping = new BeanToBeanMapping(MyBeanBA.class,
				MyBeanBATransferObject.class);
		beanToBean.addBeanToBeanMapping(beanToBeanMapping);

		MyBeanAB myBeanAB = new MyBeanAB();
		myBeanAB.setId(55L);
		List<Integer> listOfInteger = new ArrayList<Integer>();
		listOfInteger.add(1);
		listOfInteger.add(2);
		listOfInteger.add(3);
		myBeanAB.setMyIntegers(listOfInteger);
		MyBeanBA myBeanBA = new MyBeanBA();
		myBeanBA.setId(56L);
		myBeanBA.setMyNumber(75);
		myBeanAB.setMyBeanBA(myBeanBA);
		myBeanBA.setMyBeanAB(myBeanAB);

		MyBeanABTransferObject myBeanABTransferObject = (MyBeanABTransferObject) converter
				.convert(myBeanAB, MyBeanABTransferObject.class);
		assertNotNull(myBeanABTransferObject);
		assertEquals(1, myBeanABTransferObject.getMyIntegers()[0]);
		assertEquals(2, myBeanABTransferObject.getMyIntegers()[1]);
		assertEquals(3, myBeanABTransferObject.getMyIntegers()[2]);
		assertNotNull(myBeanABTransferObject.getMyBeanBA());
		assertEquals(75, myBeanABTransferObject.getMyBeanBA().getMyNumber());
		assertTrue(myBeanABTransferObject == myBeanABTransferObject
				.getMyBeanBA().getMyBeanAB());
	}
	
	public void testBeanToBean() throws Exception {
		BeanToBean beanToBean = new BeanToBean();
		IConverter[] converters = new IConverter[] { new NumberToNumber(),
				new StringToNumber(), new StringToClass(), new ArrayToArray(),
				new MapToMap(), new ArrayToCollection(),
				new CollectionToCollection(), new CollectionToArray(),
				new StringToFile(), new StringToURL(),
				new CharacterArrayToString(), new StringToCharacterArray(),
				new ObjectToString(), new DateToCalendar(),
				new IdentityConverter(), beanToBean };
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		BeanToBeanMapping beanToBeanMapping = new BeanToBeanMapping(
				MyBean4.class, MyBean4TransferObject.class);
		beanToBeanMapping.addMapping("size", "length");
		beanToBean.addBeanToBeanMapping(beanToBeanMapping);

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("hello world");
		myBean4.setSize(55);
		List<String> myStrings = new ArrayList<String>();
		myStrings.add("first");
		myStrings.add("second");
		myStrings.add("third");
		myBean4.setMyStrings(myStrings);

		MyBean4TransferObject myBean4TransferObject = (MyBean4TransferObject) converter
				.convert(myBean4, MyBean4TransferObject.class);
		assertEquals("hello world", myBean4TransferObject.getMyString());
		assertEquals("first", myBean4TransferObject.getMyStrings()[0]);
		assertEquals("second", myBean4TransferObject.getMyStrings()[1]);
		assertEquals("third", myBean4TransferObject.getMyStrings()[2]);
		assertEquals(55, myBean4TransferObject.getLength());
	}	
	
	
}
