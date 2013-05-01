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
package net.entropysoft.transmorph;

import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.context.ConvertedObjectPool;
import net.entropysoft.transmorph.context.UsedConverters;

/**
 * The conversion context.
 * 
 * <p>
 * You can add custom objects to the context using add method.
 * </p>
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class ConversionContext {
	private final ConvertedObjectPool convertedObjectPool = new ConvertedObjectPool();
	private final Map<String, Object> map = new HashMap<String, Object>();
	private final UsedConverters usedConverters = new UsedConverters();
	private boolean storeUsedConverters = false;

	public boolean isStoreUsedConverters() {
		return storeUsedConverters;
	}

	public void setStoreUsedConverters(boolean storeUsedConverters) {
		this.storeUsedConverters = storeUsedConverters;
	}

	/**
	 * get the pool of converted objects
	 * 
	 * @return
	 */
	public ConvertedObjectPool getConvertedObjectPool() {
		return convertedObjectPool;
	}

	/**
	 * get the converters that have been used for conversion
	 * 
	 * @return
	 */
	public UsedConverters getUsedConverters() {
		return usedConverters;
	}

	/**
	 * add an object to the context
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * get object from the context
	 * 
	 * @param key
	 */
	public Object get(String key) {
		return map.get(key);
	}

	/**
	 * remove object from the context
	 * 
	 * @param key
	 */
	public void remove(String key) {
		map.remove(key);
	}

}
