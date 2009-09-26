package net.entropysoft.transmorph.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

import samples.SerializableBean;

public class SerializableConverterTest {

	@Test
	public void testSerializableConverter() throws Exception {
		Transmorph converter = new Transmorph(new SerializableConverter());

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
