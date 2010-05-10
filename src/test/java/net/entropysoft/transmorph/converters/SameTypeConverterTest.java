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
import static org.junit.Assert.assertNull;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;


public class SameTypeConverterTest {

	@Test
	public void testSameTypeConverter() throws Exception {
		SameTypeConverter sameTypeConverter = new SameTypeConverter();
		sameTypeConverter.setElementConverter(new DefaultConverters());
		Transmorph converter = new Transmorph(sameTypeConverter);
		assertEquals(55,converter.convert(new Integer(55), Object.class));
		assertNull(converter.convert(null, Object.class));
	}
	
}
