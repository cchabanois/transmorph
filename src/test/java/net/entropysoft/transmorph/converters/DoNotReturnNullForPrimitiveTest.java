package net.entropysoft.transmorph.converters;

import static org.junit.Assert.fail;

import java.text.MessageFormat;

import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.IConverter;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.beans.BeanToBean;
import net.entropysoft.transmorph.converters.beans.BeanToMap;
import net.entropysoft.transmorph.converters.beans.MapToBean;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;
import net.entropysoft.transmorph.converters.collections.ArrayToCollection;
import net.entropysoft.transmorph.converters.collections.ArrayToSingleElement;
import net.entropysoft.transmorph.converters.collections.ArrayToString;
import net.entropysoft.transmorph.converters.collections.CollectionToArray;
import net.entropysoft.transmorph.converters.collections.CollectionToSingleElement;
import net.entropysoft.transmorph.converters.collections.CollectionToString;
import net.entropysoft.transmorph.converters.collections.MapToMap;
import net.entropysoft.transmorph.converters.collections.MapToString;
import net.entropysoft.transmorph.converters.enums.EnumToEnum;
import net.entropysoft.transmorph.converters.enums.StringToEnum;
import net.entropysoft.transmorph.converters.propertyeditors.FromStringUsingPropertyEditor;
import net.entropysoft.transmorph.converters.propertyeditors.ToStringUsingPropertyEditor;

import org.junit.Test;

public class DoNotReturnNullForPrimitiveTest {
	private IConverter[] converters = new IConverter[] { new BeanToBean(),
			new BeanToMap(), new MapToBean(), new ArrayToArray(),
			new ArrayToCollection(), new ArrayToSingleElement(),
			new ArrayToString(), new CollectionToArray(),
			new CollectionToSingleElement(), new CollectionToString(),
			new MapToMap(), new MapToString(), new EnumToEnum(),
			new StringToEnum(), new FromStringUsingPropertyEditor(),
			new ToStringUsingPropertyEditor(), new CalendarToDate(),
			new CharacterArrayToString(), new ClassToString(),
			new CloneableConverter(), new DateToCalendar(),
			new FormattedStringToNumber(), new IdentityConverter(),
			new ImmutableIdentityConverter(), new NumberToNumber(),
			new ObjectToFormattedString(),
			new ObjectToObjectUsingConstructor(), new ObjectToString(),
			new SerializableConverter(), new SingleElementToArray(),
			new SingleElementToCollection(), new StaticConverter(),
			new StringToBoolean(), new StringToCalendar(),
			new StringToCharacterArray(),
			new StringToClass(getClass().getClassLoader()), new StringToDate(),
			new StringToFile(), new StringToInputStream(),
			new StringToNumber(), new StringToQName(),
			new StringToStringBuffer(), new StringToStringBuilder(),
			new StringToTimeZone(), new StringToURI(), new StringToURL(),
			new URIToURL(), new URLToURI() };

	@Test
	public void testDoNotReturnNullForPrimitive() {
		for (IConverter converter : converters) {
			Transmorph transmorph = new Transmorph(converter);
			try {
				int myInt = transmorph.convert(null, Integer.TYPE);
				if (myInt == 0) {
					System.out.println("just to avoid removing of code");
				}
			} catch (ConverterException e) {

			} catch (NullPointerException e) {
				fail(MessageFormat.format(
						"''{0}'' return null for primitive destination type",
						converter.getClass().getName()));
			}
		}
	}

}
