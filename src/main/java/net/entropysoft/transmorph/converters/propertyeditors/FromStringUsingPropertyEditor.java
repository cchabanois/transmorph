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

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractConverter;
import net.entropysoft.transmorph.type.TypeReference;

/**
 * Converter that converts from string using a {@link PropertyEditor}
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class FromStringUsingPropertyEditor extends AbstractConverter {

	private IPropertyEditorProvider propertyEditorProvider = new DefaultPropertyEditorProvider();

	public IPropertyEditorProvider getPropertyEditorProvider() {
		return propertyEditorProvider;
	}

	public void setPropertyEditorProvider(
			IPropertyEditorProvider propertyEditorProvider) {
		this.propertyEditorProvider = propertyEditorProvider;
	}

	@Override
	protected boolean canHandleDestinationType(TypeReference<?> destinationType) {
		return propertyEditorProvider.getPropertyEditor(destinationType
				.getRawType()) != null;
	}

	@Override
	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject instanceof String;
	}

	@Override
	public Object doConvert(ConversionContext context, Object sourceObject,
			TypeReference<?> destinationType) throws ConverterException {
		if (sourceObject == null) {
			return null;
		}
		PropertyEditor propertyEditor;
		propertyEditor = propertyEditorProvider
				.getPropertyEditor(destinationType.getRawType());

		String text = (String) sourceObject;
		propertyEditor.setAsText(text);

		return propertyEditor.getValue();
	}

}
