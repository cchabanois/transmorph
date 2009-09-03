package net.entropysoft.transmorph.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import samples.SerializableBean;

import net.entropysoft.transmorph.Transmorph;
import junit.framework.TestCase;

public class SerializableConverterTest extends TestCase {

	public void testSerializableConverter() throws Exception {
		Transmorph converter = new Transmorph(SerializableConverterTest.class
				.getClassLoader(), new SerializableConverter());

		SerializableBean serializableBean = new SerializableBean();
		serializableBean.setMyString("Hello world");
		serializableBean.setMyListOfStrings(Arrays.asList("first", "second"));
		
		SerializableBean clone = converter.convert(serializableBean, SerializableBean.class);
		assertNotNull(clone);
		assertEquals("Hello world", clone.getMyString());
		assertEquals("first", clone.getMyListOfStrings().get(0));
		assertEquals("second", clone.getMyListOfStrings().get(1));
		
		clone = converter.convert(null, SerializableBean.class);
		assertEquals(null, clone);
	}
	
}
