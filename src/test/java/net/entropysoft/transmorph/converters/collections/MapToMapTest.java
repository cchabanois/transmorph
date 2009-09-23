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
package net.entropysoft.transmorph.converters.collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.type.TypeReference;

public class MapToMapTest extends TestCase {

	public void testMapToMap() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

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
		TypeReference<Map<String,List<String>>> typeReference = new TypeReference<Map<String,List<String>>>() {
		};
		Map<String, List<String>> converted = (Map<String, List<String>>) converter
				.convert(context, map, typeReference);
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
		Transmorph converter = new Transmorph(new DefaultConverters());

		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("key1", 1);
		map.put("key2", 2);

		Properties properties = converter.convert(map, Properties.class);
		assertNotNull(properties);
		assertEquals("1", properties.get("key1"));
		assertEquals("2", properties.get("key2"));
	}

}
