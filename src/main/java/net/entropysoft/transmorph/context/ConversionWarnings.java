package net.entropysoft.transmorph.context;

import java.util.ArrayList;
import java.util.List;

public class ConversionWarnings {
	private List<IConversionWarningsListener> conversionWarningListeners = new ArrayList<IConversionWarningsListener>();
	
	public void addWarningListener(IConversionWarningsListener conversionWarningsListener) {
		conversionWarningListeners.add(conversionWarningsListener);
	}
	
	public void removeWarningListener(IConversionWarningsListener conversionWarningsListener) {
		conversionWarningListeners.remove(conversionWarningsListener);
	}
	
}
