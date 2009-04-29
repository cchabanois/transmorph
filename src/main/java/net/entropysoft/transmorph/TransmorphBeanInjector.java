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

import java.text.MessageFormat;
import net.entropysoft.transmorph.signature.ClassFactory;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.Type;
import net.entropysoft.transmorph.type.TypeFactory;

/**
 * Inject property values into a bean. If necessary, values will be converted.
 * 
 * <p>
 * This class is not thread-safe
 * </p>
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class TransmorphBeanInjector implements IBeanInjector {
	private IBeanInjector beanInjector;
	private TypeFactory typeFactory;

	public TransmorphBeanInjector(IBeanInjector beanInjector) {
		this(Thread.currentThread().getContextClassLoader(), beanInjector);
	}

	public TransmorphBeanInjector(ClassLoader classLoader,
			IBeanInjector beanInjector) {
		this(new TypeFactory(new ClassFactory(classLoader)), beanInjector);
	}

	public TransmorphBeanInjector(TypeFactory typeFactory,
			IBeanInjector beanInjector) {
		this.typeFactory = typeFactory;
		this.beanInjector = beanInjector;
	}

	public void inject(Object source, Object targetBean)
			throws ConverterException {
		inject(source, targetBean, targetBean.getClass());
	}

	public void inject(Object source, Object targetBean, Class targetClass)
			throws ConverterException {
		inject(new ConversionContext(), source, targetBean, targetClass);
	}

	public void inject(ConversionContext context, Object source,
			Object targetBean, Class targetClass) throws ConverterException {
		TypeSignature typeSignature = TypeSignatureFactory
				.getTypeSignature(targetClass);
		Type destinationType = typeFactory.getType(typeSignature);
		inject(context, source, targetBean, destinationType);
	}

	public void inject(ConversionContext context, Object source,
			Object targetBean, Type targetType) throws ConverterException {
		try {
			beanInjector.inject(context, source, targetBean, targetType);
		} catch (ConverterException e) {
			throw new ConverterException(MessageFormat.format(
					"Could not inject into bean ''{0}'' from object ''{1}''",
					targetBean.getClass().getName(), source.getClass()
							.getName()),e);
		}
	}

	public boolean canHandle(Object sourceObject, Type targetType) {
		return beanInjector.canHandle(sourceObject, targetType);
	}

	public IConverter getPropertyValueConverter() {
		return beanInjector.getPropertyValueConverter();
	}

	public void setPropertyValueConverter(IConverter converter) {
		beanInjector.setPropertyValueConverter(converter);
	}

}
