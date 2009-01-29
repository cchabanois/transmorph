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

import java.io.File;
import java.net.URL;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.Converters;

public class ObjectToObjectUsingConstructorTest extends TestCase {

	public void testStringToObjectUsingConstructor() throws Exception {
		ObjectToObjectUsingConstructor objectToObjectUsingConstructor = new ObjectToObjectUsingConstructor();
		objectToObjectUsingConstructor.setHandledDestinationClasses(ObjectToObjectUsingConstructor.ALL_DESTINATION_CLASSES);
		
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(),
				new Converters(objectToObjectUsingConstructor));
		File file = (File) converter.convert("c:\temp", File.class);
		assertNotNull(file);
		
		URL url = (URL) converter.convert("http://www.entropysoft.net",
				URL.class);
		assertEquals("http://www.entropysoft.net", url.toString());
	}
	
	public void testStringToUrl() throws Exception {
		ObjectToObjectUsingConstructor objectToObjectUsingConstructor = new ObjectToObjectUsingConstructor();

		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(),
				new Converters(objectToObjectUsingConstructor));

		objectToObjectUsingConstructor
				.setHandledDestinationClasses(new Class[] { URL.class });
		try {
			File file = (File) converter.convert("c:\temp", File.class);
			fail("Convertion should have failed");
		} catch (ConverterException e) {

		}

		URL url = (URL) converter.convert("http://www.entropysoft.net",
				URL.class);
		assertEquals("http://www.entropysoft.net", url.toString());
	}
	
	
}
