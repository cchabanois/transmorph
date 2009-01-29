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

import junit.framework.TestCase;
import net.entropysoft.transmorph.Converter;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.modifiers.CanonicalizeFile;
import net.entropysoft.transmorph.modifiers.IModifier;

public class StringToFileTest extends TestCase {

	public void testStringToFile() throws Exception {
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), TestConverters.converters);

		File file = (File) converter.convert("c:\\temp", File.class);
		assertNotNull(file);
		assertEquals("c:\\temp", file.toString());
	}

	public void testStringToFileCanonical() throws Exception {
		StringToFile stringToFile = new StringToFile();
		stringToFile.setModifiers(new IModifier[] { new CanonicalizeFile() });
		Converter converter = new Converter(ConverterTest.class
				.getClassLoader(), new IConverter[] { stringToFile });
		File file = (File) converter.convert("temp", File.class);
		assertNotNull(file);
		// getCanonicalFile is OS-dependant
		assertEquals((new File("temp")).getCanonicalFile(), file);
	}
	
	
}