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
package net.entropysoft.transmorph.converters;

import java.util.List;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;
import net.entropysoft.transmorph.converters.collections.ArrayToCollection;
import net.entropysoft.transmorph.converters.collections.CollectionToCollection;
import net.entropysoft.transmorph.converters.collections.MapToMap;
import net.entropysoft.transmorph.converters.enums.StringToEnum;

public class SingleElementToCollectionTest extends TestCase {

	public void testSingleElementToCollection() throws Exception {
		IConverter[] converters = new IConverter[] { new NumberToNumber(),
				new StringToNumber(), new StringToBoolean(),
				new StringToEnum(), new ArrayToArray(), new MapToMap(),
				new ArrayToCollection(), new CollectionToCollection(),
				new ObjectToString(), new SingleElementToCollection(),
				new IdentityConverter() };

		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), converters);

		List<Integer> list = (List<Integer>) converter.convert("55",
				List.class, new Class[] { Integer.class });
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals(55, list.get(0).intValue());
	}	
	
}
