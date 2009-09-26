package net.entropysoft.transmorph.converters.collections;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.ObjectToString;

import org.junit.Test;

public class MapToStringTest {

	@Test
	public void testMapToString() throws Exception {
		Transmorph converter = new Transmorph(new MapToString(), new ArrayToString(),
				new ObjectToString());

		// Map[String, String[]] => String
		Map<String, String[]> map = new LinkedHashMap<String, String[]>();
		map.put("key1", new String[] { "value1-1", "value1-2" });
		map.put("key2", new String[] { "value2-1", "value2-2" });
		map.put("key3", null);
		map.put("key4", new String[] { null, null });
		map.put(null, new String[] { "value5-1", "value5-2" });
		String converted = converter.convert(map, String.class);
		assertEquals(
				"{key1=[value1-1, value1-2], key2=[value2-1, value2-2], key3=null, key4=[null, null], null=[value5-1, value5-2]}",
				converted);
	}

}
