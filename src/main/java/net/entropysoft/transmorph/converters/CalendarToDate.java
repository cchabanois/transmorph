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
package net.entropysoft.transmorph.converters;

import java.util.Calendar;
import java.util.Date;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.type.Type;

/**
 * Converter used when source object is an Calendar and destination type is
 * Date
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class CalendarToDate extends AbstractSimpleConverter<Calendar, Date>  {

	public CalendarToDate() {
		super(Calendar.class, Date.class);
	}

	@Override
	public Date doConvert(IConverter elementConverter, Calendar sourceObject,
			Type destinationType) throws ConverterException {
		return sourceObject.getTime();
	}

}
