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
package net.entropysoft.transmorph.modifiers;

import java.io.File;
import java.io.IOException;

/**
 * Modifier that calls getCononicalFile on File
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class CanonicalizeFile implements IModifier<File> {

	public File modify(File object) throws ModifierException {
		if (object == null) {
			return null;
		}
		try {
			return object.getCanonicalFile();
		} catch (IOException e) {
			throw new ModifierException("Cannot get canonical form for '"
					+ object.toString() + "'", e);
		}
	}

}
