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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.net.URL;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class ObjectToStringTest {

	@Test
	public void testObjectToString() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		ObjectToString objectToString = defaultConverters.getObjectToString();

		Transmorph converter = new Transmorph(defaultConverters);
		String str = converter.convert(new URL("http://www.entropysoft.net"),
				String.class);
		assertEquals("http://www.entropysoft.net", str);
		str = converter.convert(new File("c:\temp"), String.class);
		assertEquals("c:\temp", str);

		objectToString.setHandledSourceClasses(new Class[] { URL.class });
		try {
			str = converter.convert(new File("c:\temp"), String.class);
			fail("Convertion should have failed");
		} catch (ConverterException e) {

		}

		str = converter.convert(new URL("http://www.entropysoft.net"),
				String.class);
		assertEquals("http://www.entropysoft.net", str);
	}

	@Test
	public void testObjectToStringNotOverridden() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		ObjectToString objectToString = defaultConverters.getObjectToString();

		Transmorph converter = new Transmorph(defaultConverters);

		try {
			String str = converter.convert(
					new MyClassWithToStringNotOverriden(), String.class);
			fail("Conversion should have failed");
		} catch (ConverterException e) {

		}
		assertEquals("my string", converter.convert(
				new MyClassWithToStringOverriden("my string"), String.class));

	}

	private static class MyClassWithToStringNotOverriden {
		private String myString;

	}

	private static class MyClassWithToStringOverriden {
		private String myString;

		public MyClassWithToStringOverriden(String value) {
			this.myString = value;
		}

		@Override
		public String toString() {
			return myString;
		}
	}

}
