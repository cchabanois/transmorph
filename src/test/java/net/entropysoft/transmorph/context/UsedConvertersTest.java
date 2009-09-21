package net.entropysoft.transmorph.context;

import java.text.MessageFormat;

import junit.framework.TestCase;
import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.converters.MultiConverter;
import net.entropysoft.transmorph.converters.NumberToNumber;
import net.entropysoft.transmorph.converters.collections.ArrayToArray;

public class UsedConvertersTest extends TestCase {

	public void testUsedConverters() throws Exception {
		DefaultConverters defaultConverters = new DefaultConverters();
		NumberToNumber numberToNumber = defaultConverters.getNumberToNumber();
		numberToNumber.setNullReplacementForPrimitive(0);

		ConversionContext conversionContext = new ConversionContext();
		conversionContext.setStoreUsedConverters(true);

		Transmorph converter = new Transmorph(defaultConverters);
		long[] longsArray = (long[]) converter.convert(conversionContext,
				new Integer[] { 1, 2, null, 4 }, long[].class);

		UsedConverters usedConverters = conversionContext.getUsedConverters();
		assertConverterUsed(usedConverters, NumberToNumber.class);
		assertConverterUsed(usedConverters, ArrayToArray.class);

		// these converters are not added to the used converters because this
		// would not be very useful
		assertConverterNotUsed(usedConverters, DefaultConverters.class);
		assertConverterNotUsed(usedConverters, MultiConverter.class);
	}

	private void assertConverterUsed(UsedConverters usedConverters,
			Class converterClass) {
		for (UsedConverter usedConverter : usedConverters.getUsedConverters()) {
			if (usedConverter.getConverter().getClass().equals(converterClass)) {
				return;
			}
		}
		fail(MessageFormat.format("Converter ''{0}'' not used", converterClass
				.getName()));
	}

	private void assertConverterNotUsed(UsedConverters usedConverters,
			Class converterClass) {
		for (UsedConverter usedConverter : usedConverters.getUsedConverters()) {
			if (usedConverter.getConverter().getClass().equals(converterClass)) {
				fail(MessageFormat.format("Converter ''{0}'' used",
						converterClass.getName()));
			}
		}
	}
}
