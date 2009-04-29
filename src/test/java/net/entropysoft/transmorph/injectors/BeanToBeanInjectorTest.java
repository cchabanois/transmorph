package net.entropysoft.transmorph.injectors;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.TransmorphBeanInjector;
import net.entropysoft.transmorph.converters.beans.BeanToBeanMapping;
import samples.MyBean4;
import samples.MyBean4TransferObject;

public class BeanToBeanInjectorTest extends TestCase {

	public void testBeanInjectionFromBean() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		BeanToBeanInjector beanInjector = new BeanToBeanInjector();
		beanInjector.setPropertyValueConverter(defaultConverters);
		TransmorphBeanInjector transmorphBeanInjector = new TransmorphBeanInjector(
				BeanToBeanInjectorTest.class.getClassLoader(), beanInjector);

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

}
