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
package net.entropysoft.transmorph.converters.propertyeditors;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;

/**
 * PropertyEditorProvider that uses the {@link PropertyEditorManager} to get the property editors
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class DefaultPropertyEditorProvider implements IPropertyEditorProvider {

	public PropertyEditor getPropertyEditor(Class targetType) {
		return PropertyEditorManager.findEditor(targetType);
	}

	public void registerPropertyEditor(Class<?> targetType, Class<?> editorClass) {
		PropertyEditorManager.registerEditor(targetType, editorClass);
	}

}
