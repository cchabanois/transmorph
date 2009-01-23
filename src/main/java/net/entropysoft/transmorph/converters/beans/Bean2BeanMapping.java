package net.entropysoft.transmorph.converters.beans;

import java.util.HashMap;
import java.util.Map;

import net.entropysoft.transmorph.type.Type;

public class Bean2BeanMapping {

	private Class sourceClass;
	private Class destinationClass;
	private Map<String, String> destinationPropertyName2SourcePropertyNameMap = new HashMap<String, String>();
	private Map<String,Type> destinationPropertyName2Type = new HashMap<String, Type>();
	
	public Bean2BeanMapping(Class sourceClass, Class destinationClass) {
		this.sourceClass = sourceClass;
		this.destinationClass = destinationClass;
	}

	public void addMapping(String sourceProperty, String destinationProperty) {
		destinationPropertyName2SourcePropertyNameMap.put(destinationProperty, sourceProperty);
	}
	
	public void setDestinationPropertyType(String destinationProperty, Type destinationType) {
		destinationPropertyName2Type.put(destinationProperty, destinationType);
	}
	
	public String getSourceProperty(String destinationProperty) {
		return destinationPropertyName2SourcePropertyNameMap.get(destinationProperty);
	}

}
