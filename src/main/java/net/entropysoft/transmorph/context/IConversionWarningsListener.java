package net.entropysoft.transmorph.context;

import net.entropysoft.transmorph.ConverterException;

public interface IConversionWarningsListener {

	void handleWarning(IConversionWarning conversionWarning) throws ConverterException;
	
}
