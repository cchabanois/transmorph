package net.entropysoft.transmorph.injectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.TransmorphBeanInjector;
import net.entropysoft.transmorph.converters.ImmutableIdentityConverter;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.WrapperToPrimitive;
import net.entropysoft.transmorph.converters.beans.BeanToBean;
import net.entropysoft.transmorph.converters.beans.BeanToBeanMapping;
import net.entropysoft.transmorph.converters.collections.CollectionToCollection;
import net.entropysoft.transmorph.converters.collections.MapToMap;

import org.junit.Test;

import samples.MyBean4;
import samples.MyBean4Ancestor;
import samples.MyBean4TransferObject;
import samples.MyBeanAB;
import samples.MyBeanBA;

public class BeanToBeanInjectorTest {

	@Test
	public void testBeanToBeanInjectorNoConversion() throws Exception {
		BeanToBeanInjector beanInjector = new BeanToBeanInjector();
		TransmorphBeanInjector transmorphBeanInjector = new TransmorphBeanInjector(
				beanInjector);

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("hello world");
		myBean4.setSize(55);
		List<String> myStrings = new ArrayList<String>();
		myStrings.add("first");
		myStrings.add("second");
		myStrings.add("third");
		myBean4.setMyStrings(myStrings);

		MyBean4 myBean4Injected = new MyBean4();
		transmorphBeanInjector.inject(myBean4, myBean4Injected);

		assertEquals("hello world", myBean4Injected.getMyString());
		assertEquals(55, myBean4Injected.getSize());
		assertEquals(myStrings, myBean4Injected.getMyStrings());
	}

	@Test
	public void testBeanToBeanSubClassToAncestor() throws Exception {
		BeanToBeanInjector beanInjector = new BeanToBeanInjector();
		TransmorphBeanInjector transmorphBeanInjector = new TransmorphBeanInjector(
				beanInjector);

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("hello world");
		myBean4.setSize(55);
		List<String> myStrings = new ArrayList<String>();
		myStrings.add("first");
		myStrings.add("second");
		myStrings.add("third");
		myBean4.setMyStrings(myStrings);

		MyBean4Ancestor myBean4Injected = new MyBean4Ancestor();
		transmorphBeanInjector.inject(myBean4, myBean4Injected);

		assertEquals(55, myBean4Injected.getSize());
	}

	@Test
	public void testBeanToBeanInjector() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		BeanToBeanInjector beanInjector = new BeanToBeanInjector();
		beanInjector.setPropertyValueConverter(defaultConverters);
		TransmorphBeanInjector transmorphBeanInjector = new TransmorphBeanInjector(
				beanInjector);

		BeanToBeanMapping beanToBeanMapping = new BeanToBeanMapping(
				MyBean4.class, MyBean4TransferObject.class);
		beanToBeanMapping.addMapping("size", "length");
		beanInjector.addBeanToBeanMapping(beanToBeanMapping);

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("hello world");
		myBean4.setSize(55);
		List<String> myStrings = new ArrayList<String>();
		myStrings.add("first");
		myStrings.add("second");
		myStrings.add("third");
		myBean4.setMyStrings(myStrings);

		MyBean4TransferObject myBean4TransferObject = new MyBean4TransferObject();

		transmorphBeanInjector.inject(myBean4, myBean4TransferObject);

		assertEquals("hello world", myBean4TransferObject.getMyString());
		assertEquals("first", myBean4TransferObject.getMyStrings()[0]);
		assertEquals("second", myBean4TransferObject.getMyStrings()[1]);
		assertEquals("third", myBean4TransferObject.getMyStrings()[2]);
		assertEquals(55, myBean4TransferObject.getLength());
	}

	@Test
	public void testCopyBeanUsingInjector() throws Exception {
		TransmorphBeanInjector transmorphBeanInjector = new TransmorphBeanInjector(
				new BeanToBeanInjector());
		transmorphBeanInjector.setPropertyValueConverter(new MultiConverter(
				new BeanToBean(), new ImmutableIdentityConverter(),
				new WrapperToPrimitive(), new CollectionToCollection(),
				new MapToMap()));

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

		MyBeanAB myBeanABCopy = new MyBeanAB();
		transmorphBeanInjector.inject(myBeanAB, myBeanABCopy);

		assertFalse(myBeanAB == myBeanABCopy);
		assertEquals(1, myBeanABCopy.getMyIntegers().get(0).intValue());
		assertEquals(2, myBeanABCopy.getMyIntegers().get(1).intValue());
		assertEquals(3, myBeanABCopy.getMyIntegers().get(2).intValue());
		assertNotNull(myBeanABCopy.getMyBeanBA());
		assertEquals(75, myBeanABCopy.getMyBeanBA().getMyNumber());

		// note that for this reason, you should use Transmorph instead when you
		// want to make a copy of an object
		assertFalse(myBeanABCopy == myBeanABCopy.getMyBeanBA().getMyBeanAB());

	}

}
