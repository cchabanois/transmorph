package net.entropysoft.transmorph.converters.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.IdentityConverter;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

import samples.MyBean4;

public class BeanToMapTest {

	@Test
	public void testBeanToMap() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		Transmorph converter = new Transmorph(new BeanToMap(), new IdentityConverter());

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
		List<String> listOfStrings = (List<String>)map.get("myStrings");
		assertEquals("[first, second, third]", listOfStrings.toString());
	}

}
