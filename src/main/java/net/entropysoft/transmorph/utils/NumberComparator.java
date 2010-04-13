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
package net.entropysoft.transmorph.utils;

import java.util.Comparator;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.NumberToNumber;

/**
 * Comparator for {@link Number}s
 * 
 * @author cedric
 *
 */
public class NumberComparator implements Comparator<Number> {
	private final Transmorph transmorph;

	public NumberComparator() {
		NumberToNumber numberToNumber = new NumberToNumber();
		numberToNumber.setCheckOutOfRange(false);
		this.transmorph = new Transmorph(numberToNumber);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Number number1, Number number2) {
		Class<? extends Number> commonClass = NumberUtils.getCommonNumberClass(
				number1.getClass(), number2.getClass());
		try {
			Comparable number1WithCommonClass = (Comparable) transmorph
					.convert(number1, commonClass);
			Comparable number2WithCommonClass = (Comparable) transmorph
					.convert(number2, commonClass);

			return number1WithCommonClass.compareTo(number2WithCommonClass);
		} catch (ConverterException e) {
			throw new RuntimeException(e);
		}
	}

}
