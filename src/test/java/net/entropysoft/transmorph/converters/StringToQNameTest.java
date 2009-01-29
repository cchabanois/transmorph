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

import javax.xml.namespace.QName;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.ConverterTest;
import net.entropysoft.transmorph.DefaultConverters;

public class StringToQNameTest extends TestCase {

	public void testStringToQName() throws Exception {
		Transmorph converter = new Transmorph(ConverterTest.class
				.getClassLoader(), new DefaultConverters());

		QName qname = (QName) converter.convert(
				"{http://www.entropysoft.ney}localPart", QName.class);
		assertNotNull(qname);
	}	
	
}
