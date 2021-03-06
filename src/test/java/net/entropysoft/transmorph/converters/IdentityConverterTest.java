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
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class IdentityConverterTest {

	@Test
	public void testIdentity() throws Exception {
		Transmorph converter = new Transmorph(new DefaultConverters());

		assertSame("my string", converter.convert("my string", String.class));

		// this will be handled by NumberToNumber
		Integer myInteger = new Integer(80);
		assertSame(myInteger, converter.convert(myInteger, Integer.class));

		// an other array list will be created ...
		ArrayList arrayList = new ArrayList();
		arrayList.add(80);
		assertEquals(arrayList, converter.convert(arrayList, List.class));
	}


}
