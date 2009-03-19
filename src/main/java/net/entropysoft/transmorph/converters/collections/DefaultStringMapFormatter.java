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


public class DefaultStringMapFormatter implements IStringMapFormatter {

	public String format(String[] keys, String[] values) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");

		for (int i = 0; i < keys.length; i++) {
			if (i != 0) {
				stringBuilder.append(", ");
			}
			stringBuilder.append(keys[i]);
			stringBuilder.append('=');
			stringBuilder.append(values[i]);
		}
		stringBuilder.append("}");
		return stringBuilder.toString();
		
	}

}
