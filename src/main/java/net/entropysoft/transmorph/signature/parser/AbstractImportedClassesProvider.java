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
package net.entropysoft.transmorph.signature.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Base implementation for IImportedClassesProvider
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public abstract class AbstractImportedClassesProvider implements IImportedClassesProvider {
	protected Map<String, String> importedClasses = new HashMap<String, String>();

	protected void importClass(String fullyQualifiedClassName) {
		String shortName = getShortName(fullyQualifiedClassName);
		importedClasses.put(shortName, fullyQualifiedClassName);
	}

	protected String getShortName(String shortOrFullyQualifiedName) {
		int lastDot = shortOrFullyQualifiedName.lastIndexOf(".");
		if (lastDot == -1) {
			return shortOrFullyQualifiedName;
		} else {
			return shortOrFullyQualifiedName.substring(lastDot + 1);
		}
	}

	public String getFullyQualifiedName(String shortName) {
		String fullyQualifiedName = importedClasses.get(shortName);
		if (fullyQualifiedName != null) {
			return fullyQualifiedName;
		} else {
			return shortName;
		}
	}

	public boolean isImported(String shortOrFullyQualifiedName) {
		return importedClasses.containsKey(getShortName(shortOrFullyQualifiedName));
	}

}
