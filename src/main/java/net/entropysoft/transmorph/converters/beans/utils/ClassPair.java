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
package net.entropysoft.transmorph.converters.beans.utils;

public class ClassPair {
	private Class sourceClass;
	private Class destinationClass;
	
	public ClassPair(Class sourceClass, Class destinationClass) {
		super();
		this.sourceClass = sourceClass;
		this.destinationClass = destinationClass;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((destinationClass == null) ? 0 : destinationClass
						.hashCode());
		result = prime * result
				+ ((sourceClass == null) ? 0 : sourceClass.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClassPair other = (ClassPair) obj;
		if (destinationClass == null) {
			if (other.destinationClass != null)
				return false;
		} else if (!destinationClass.equals(other.destinationClass))
			return false;
		if (sourceClass == null) {
			if (other.sourceClass != null)
				return false;
		} else if (!sourceClass.equals(other.sourceClass))
			return false;
		return true;
	}
	
	
}