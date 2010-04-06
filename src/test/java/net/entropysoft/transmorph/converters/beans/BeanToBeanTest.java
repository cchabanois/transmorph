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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.converters.ImmutableIdentityConverter;
import net.entropysoft.transmorph.converters.WrapperToPrimitive;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;
import net.entropysoft.transmorph.converters.collections.CollectionToCollection;
import net.entropysoft.transmorph.converters.collections.MapToMap;
import net.entropysoft.transmorph.signature.formatter.JavaSyntaxTypeSignatureFormatter;

import org.junit.Test;

import samples.MyBean4;
import samples.MyBean4TransferObject;
import samples.MyBeanAB;
import samples.MyBeanABTransferObject;
import samples.MyBeanBA;
import samples.MyBeanBATransferObject;
import samples.PrivatePropertyBean;
import samples.PrivatePropertyBeanTransferObject;

public class BeanToBeanTest {

	@Test
	public void testBiDirectionalBean() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		Transmorph converter = new Transmorph(defaultConverters);

		BeanToBeanMapping beanToBeanMapping = new BeanToBeanMapping(
				MyBeanAB.class, MyBeanABTransferObject.class);
		defaultConverters.getBeanToBean().addBeanToBeanMapping(
				beanToBeanMapping);

		beanToBeanMapping = new BeanToBeanMapping(MyBeanBA.class,
				MyBeanBATransferObject.class);
		defaultConverters.getBeanToBean().addBeanToBeanMapping(
				beanToBeanMapping);

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

		MyBeanABTransferObject myBeanABTransferObject = converter
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

	@Test
	public void testBeanToBean() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		Transmorph converter = new Transmorph(defaultConverters);

		BeanToBeanMapping beanToBeanMapping = new BeanToBeanMapping(
				MyBean4.class, MyBean4TransferObject.class);
		beanToBeanMapping.addMapping("size", "length");
		defaultConverters.getBeanToBean().addBeanToBeanMapping(
				beanToBeanMapping);

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("hello world");
		myBean4.setSize(55);
		List<String> myStrings = new ArrayList<String>();
		myStrings.add("first");
		myStrings.add("second");
		myStrings.add("third");
		myBean4.setMyStrings(myStrings);

		ConversionContext context = new ConversionContext();
		context.setStoreUsedConverters(true);

		MyBean4TransferObject myBean4TransferObject = converter
				.convert(context, myBean4, MyBean4TransferObject.class);
		System.out.println(context.getUsedConverters().toString(
				new JavaSyntaxTypeSignatureFormatter()));

		assertEquals("hello world", myBean4TransferObject.getMyString());
		assertEquals("first", myBean4TransferObject.getMyStrings()[0]);
		assertEquals("second", myBean4TransferObject.getMyStrings()[1]);
		assertEquals("third", myBean4TransferObject.getMyStrings()[2]);
		assertEquals(55, myBean4TransferObject.getLength());
	}

	@Test
	public void testBeanToBeanWithPrivateProperties() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		Transmorph converter = new Transmorph(defaultConverters);

		BeanToBeanMapping beanToBeanMapping = new BeanToBeanMapping(
				PrivatePropertyBean.class,
				PrivatePropertyBeanTransferObject.class);
		defaultConverters.getBeanToBean().addBeanToBeanMapping(
				beanToBeanMapping);

		PrivatePropertyBean bean = new PrivatePropertyBean(55);
		bean.setPublicProperty(22);

		PrivatePropertyBeanTransferObject beanTO = converter
				.convert(bean, PrivatePropertyBeanTransferObject.class);
		assertEquals("22", beanTO.getPublicProperty());
		assertEquals("Public property:22\nPrivate property:null", beanTO
				.toString());
	}

	@Test
	public void testCopyBean() throws Exception {
		Transmorph converter = new Transmorph(new BeanToBean(),
				new ImmutableIdentityConverter(), new WrapperToPrimitive(),
				new CollectionToCollection(), new ArrayToArray(), new MapToMap());

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

		MyBeanAB myBeanABCopy = converter.convert(myBeanAB, myBeanAB
				.getClass());
		assertFalse(myBeanAB == myBeanABCopy);
		assertEquals(1, myBeanABCopy.getMyIntegers().get(0).intValue());
		assertEquals(2, myBeanABCopy.getMyIntegers().get(1).intValue());
		assertEquals(3, myBeanABCopy.getMyIntegers().get(2).intValue());
		assertNotNull(myBeanABCopy.getMyBeanBA());
		assertEquals(75, myBeanABCopy.getMyBeanBA().getMyNumber());
		assertTrue(myBeanABCopy == myBeanABCopy
				.getMyBeanBA().getMyBeanAB());

	}

	@Test
	public void testShallowCopy() throws Exception {
		BeanToBean beanToBean = new BeanToBean();
		beanToBean.setElementConverter(new IdentityConverter());
		Transmorph converter = new Transmorph(beanToBean);
		
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

		MyBeanAB myBeanABCopy = converter.convert(myBeanAB, myBeanAB
				.getClass());
		assertFalse(myBeanAB == myBeanABCopy);
		assertEquals(listOfInteger, myBeanABCopy.getMyIntegers());
		assertNotNull(myBeanABCopy.getMyBeanBA());
		assertEquals(myBeanBA, myBeanABCopy.getMyBeanBA());
	}
	
}
