package net.entropysoft.transmorph.context;

public class ConversionWarning implements IConversionWarning {

	private String message;
	
	public ConversionWarning(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	
}
