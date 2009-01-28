package net.entropysoft.transmorph.converters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterTest;
import junit.framework.TestCase;

public class MapToMapTest extends TestCase {

	public void testMapToMap() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);
		converter.setUseInternalFormFullyQualifiedName(false);

		// Map[String, String[]] => Map<String,List<String>> (MapToMapConverter
		// and ArrayToListConverter)
		Map map = new HashMap();
		map.put("key1", new String[] { "value1-1", "value1-2" });
		map.put("key2", new String[] { "value2-1", "value2-2" });
		map.put("key3", null);
		map.put("key4", new String[] { null, null });
		map.put(null, new String[] { "value5-1", "value5-2" });
		ConversionContext context = new ConversionContext();
		context.setStoreUsedConverters(true);
		Map<String, List<String>> converted = (Map<String, List<String>>) converter
				.convert(context, map,
						"Ljava.util.Map<Ljava.lang.String;Ljava.util.List<Ljava.lang.String;>;>;");
		List<String> list1 = converted.get("key1");
		assertEquals("value1-1", list1.get(0));
		assertEquals("value1-2", list1.get(1));
		List<String> list2 = converted.get("key2");
		assertEquals("value2-1", list2.get(0));
		assertEquals("value2-2", list2.get(1));
		List<String> list3 = converted.get("key3");
		assertEquals(null, list3);
		List<String> list4 = converted.get("key4");
		assertEquals(null, list4.get(0));
		assertEquals(null, list4.get(1));
		assertTrue(converted.containsKey(null));
		List<String> list5 = converted.get(null);
		assertEquals("value5-1", list5.get(0));
		assertEquals("value5-2", list5.get(1));
	}

	public void testMapToProperties() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("key1", 1);
		map.put("key2", 2);

		Properties properties = (Properties) converter.convert(map,
				Properties.class);
		assertNotNull(properties);
		assertEquals("1", properties.get("key1"));
		assertEquals("2", properties.get("key2"));
	}
	
	
}
