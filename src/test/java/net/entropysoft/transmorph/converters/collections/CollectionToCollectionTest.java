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
package net.entropysoft.transmorph.converters.collections;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;

public class CollectionToCollectionTest extends TestCase {

	public void testListToSet() throws Exception {
		Transmorph converter = new Transmorph(CollectionToCollectionTest.class
				.getClassLoader(), new DefaultConverters());

		List<Integer> source = new ArrayList<Integer>();
		source.add(30);
		source.add(40);
		source.add(50);

		Set<String> set = (Set<String>) converter.convert(source, Set.class,
				new Class[] { String.class });
		assertNotNull(set);
		assertEquals(3, set.size());
		set.contains("30");
		set.contains("40");
		set.contains("50");
	}

	public void testListToConcreteSet() throws Exception {
		Transmorph converter = new Transmorph(CollectionToCollectionTest.class
				.getClassLoader(), new DefaultConverters());
		
		List<Integer> source = new ArrayList<Integer>();
		source.add(30);
		source.add(40);
		source.add(50);
		
		LinkedHashSet<String> linkedHashSet = (LinkedHashSet<String>) converter
				.convert(source, LinkedHashSet.class,
						new Class[] { String.class });
		assertNotNull(linkedHashSet);
		assertEquals(3, linkedHashSet.size());
		linkedHashSet.contains("30");
		linkedHashSet.contains("40");
		linkedHashSet.contains("50");
	}

}
