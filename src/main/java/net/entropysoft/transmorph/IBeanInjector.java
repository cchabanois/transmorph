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

import net.entropysoft.transmorph.type.TypeReference;

public interface IBeanInjector {

	public void inject(ConversionContext conversionContext, Object source,
			Object targetBean, TypeReference<?> targetType) throws ConverterException;

	public boolean canHandle(Object sourceObject, TypeReference<?> targetType);

	public void setPropertyValueConverter(IConverter converter);

	public IConverter getPropertyValueConverter();

}
