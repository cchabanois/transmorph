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
package net.entropysoft.transmorph.converters.enums;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.converters.AbstractConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Convert from an enumeration type to another one
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class EnumToEnum extends AbstractConverter {
	private Map<Enum, Map<Class<? extends Enum>, Enum>> enumToEnumMap = new HashMap<Enum, Map<Class<? extends Enum>, Enum>>();

	public EnumToEnum() {
		this.useObjectPool = true;
	}

	public void addEnumToEnum(Enum source, Enum dest) {
		Map<Class<? extends Enum>, Enum> mapDestEnum = enumToEnumMap.get(source);
		if (mapDestEnum == null) {
			mapDestEnum = new HashMap<Class<? extends Enum>, Enum>();
			enumToEnumMap.put(source, mapDestEnum);
		}
		mapDestEnum.put(dest.getClass(), dest);
	}

	public void addEnumToNull(Enum source, Class<? extends Enum> enumClass) {
		Map<Class<? extends Enum>, Enum> mapDestEnum = enumToEnumMap.get(source);
		if (mapDestEnum == null) {
			mapDestEnum = new HashMap<Class<? extends Enum>, Enum>();
			enumToEnumMap.put(source, mapDestEnum);
		}
		mapDestEnum.put(enumClass, null);
	}

	private Enum getDestinationEnum(Enum source, Type destinationType)
			throws ClassNotFoundException, ConverterException {
		Map<Class<? extends Enum>, Enum> mapDestEnum = enumToEnumMap.get(source);
		if (mapDestEnum != null) {
			if (mapDestEnum.containsKey(destinationType.getType())) {
				Enum destEnum = mapDestEnum.get(destinationType.getType());
				return destEnum;
			}
		}
		try {
			return Enum.valueOf(destinationType.getType(), source.name());
		} catch (IllegalArgumentException e) {
			throw new ConverterException(
					MessageFormat
							.format(
									"Enum type ''{0}'' has no constant with the specified name ''{1}''",
									destinationType.getName(), source.name()),
					e);
		}
	}

	public Object doConvert(ConversionContext context, Object sourceObject,
			Type destinationType) throws ConverterException {

		if (sourceObject == null) {
			return null;
		}
		Enum sourceEnum = (Enum) sourceObject;

		try {
			return getDestinationEnum(sourceEnum, destinationType);
		} catch (ClassNotFoundException e) {
			throw new ConverterException("Could not find class for "
					+ destinationType.getName());
		}
	}

	protected boolean canHandleDestinationType(Type destinationType) {
		try {
			return destinationType.getType().isEnum();
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	protected boolean canHandleSourceObject(Object sourceObject) {
		if (sourceObject == null) {
			return true;
		}
		return sourceObject.getClass().isEnum();
	}

}
