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
import static org.junit.Assert.assertNotNull;

import java.util.List;

import net.entropysoft.transmorph.Transmorph;

import org.junit.Test;

public class SingleElementToCollectionTest {

	@Test
	public void testSingleElementToCollection() throws Exception {
		Transmorph converter = new Transmorph(new SingleElementToCollection(), new StringToNumber());

		List<Integer> list = converter.convert("55", List.class,
				new Class[] { Integer.class });
		assertNotNull(list);
		assertEquals(1, list.size());
		assertEquals(55, list.get(0).intValue());
	}

}
