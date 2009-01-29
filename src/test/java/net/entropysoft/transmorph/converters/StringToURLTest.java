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

import java.net.URL;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.ConverterTest;

public class StringToURLTest extends TestCase {

	public void testStringToURL() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		URL url = (URL) converter.convert("http://www.entropysoft.net",
				URL.class);
		assertNotNull(url);
		assertEquals("http://www.entropysoft.net", url.toString());

		try {
			url = (URL) converter.convert("httpa://www.entropysoft.net",
					URL.class);
			fail("Should not have been converted");
		} catch (ConverterException e) {

		}
	}	
	
}
