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
package net.entropysoft.transmorph.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple LRU map
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 *
 * @param <K>
 * @param <V>
 */
public class LRUMap<K,V> extends LinkedHashMap<K,V> {

	private static final long serialVersionUID = 1L;
	private final int fMaxSize;

	public LRUMap(int maxSize) {
		super(maxSize, 0.75f, true);
		fMaxSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
		return size() > fMaxSize;
	}
}