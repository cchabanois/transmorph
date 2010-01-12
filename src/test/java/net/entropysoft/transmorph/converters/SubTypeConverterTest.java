/*
 * Copyright 2008-2010 the original author or authors.
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

import static org.junit.Assert.assertEquals;

import java.net.URL;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

public class SubTypeConverterTest {

	@Test
	public void testSubTypeConverter() throws Exception {
		SubTypeConverter subTypeConverter = new SubTypeConverter(TypeReference
				.get(Object.class), new TypeReference[] {
				TypeReference.get(Number.class),
				TypeReference.get(String.class) });
		subTypeConverter.setElementConverter(new DefaultConverters());
		Transmorph converter = new Transmorph(subTypeConverter);

		assertEquals(12, converter.convert(12, Object.class));
		assertEquals("my string", converter.convert("my string", Object.class));
		assertEquals("http://www.entropysoft.net", converter.convert(new URL(
				"http://www.entropysoft.net"), Object.class));
	}

}
