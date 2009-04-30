package net.entropysoft.transmorph.injectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.IBeanInjector;
import net.entropysoft.transmorph.TransmorphBeanInjector;
import samples.MyBean1;
import junit.framework.TestCase;

public class MapToBeanTest extends TestCase {

	public void testMapToBean() throws Exception {
		MyBean1 myBean1 = new MyBean1();
		IBeanInjector beanInjector = new MapToBeanInjector();
		beanInjector.setPropertyValueConverter(new DefaultConverters());

		TransmorphBeanInjector transmorphBeanInjector = new TransmorphBeanInjector(
				beanInjector);
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("myInt", "55");
		properties.put("myListOfStrings", new int[] { 1, 2, 3 });

		transmorphBeanInjector.inject(properties, myBean1);
		assertEquals(55, myBean1.getMyInt());
		List<String> listOfStrings = myBean1.getMyListOfStrings();
		assertEquals("1", listOfStrings.get(0));
		assertEquals("2", listOfStrings.get(1));
		assertEquals("3", listOfStrings.get(2));
		assertEquals(null, myBean1.getMyBean2());
	}

	public void testMapToBeanTooManyProperties() throws Exception {
		MyBean1 myBean1 = new MyBean1();
		IBeanInjector beanInjector = new MapToBeanInjector();
		beanInjector.setPropertyValueConverter(new DefaultConverters());

		TransmorphBeanInjector transmorphBeanInjector = new TransmorphBeanInjector(
				beanInjector);
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("myInt", "55");
		properties.put("myListOfStrings", new int[] { 1, 2, 3 });
		properties.put("myInt2", "55");

		try {
			transmorphBeanInjector.inject(properties, myBean1);
			fail("Should not have been able to inject");
		} catch (ConverterException e) {

		}
	}

}
