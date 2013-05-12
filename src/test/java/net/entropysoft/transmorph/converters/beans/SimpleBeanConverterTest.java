package net.entropysoft.transmorph.converters.beans;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import net.entropysoft.transmorph.ConversionContext;
import net.entropysoft.transmorph.ConverterException;
import net.entropysoft.transmorph.DefaultConverters;
import net.entropysoft.transmorph.Transmorph;
import net.entropysoft.transmorph.signature.formatter.JavaSyntaxTypeSignatureFormatter;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

import samples.MyBean4;
import samples.MyBean4TransferObject;

public class SimpleBeanConverterTest {

	@Test
	public void testBeanToBean() throws Exception {
		// Given
		DefaultConverters defaultConverters = new DefaultConverters();
		defaultConverters.addConverter(new Bean4ToBean4TransferObject());
		Transmorph converter = new Transmorph(defaultConverters);

		MyBean4 myBean4 = new MyBean4();
		myBean4.setMyString("hello world");
		myBean4.setSize(55);
		List<String> myStrings = new ArrayList<String>();
		myStrings.add("first");
		myStrings.add("second");
		myStrings.add("third");
		myBean4.setMyStrings(myStrings);

		ConversionContext context = new ConversionContext();
		context.setStoreUsedConverters(true);

		// When
		MyBean4TransferObject myBean4TransferObject = converter
				.convert(context, myBean4, MyBean4TransferObject.class);
		System.out.println(context.getUsedConverters().toString(
				new JavaSyntaxTypeSignatureFormatter()));

		// Then
		assertEquals("hello world", myBean4TransferObject.getMyString());
		assertEquals("first", myBean4TransferObject.getMyStrings()[0]);
		assertEquals("second", myBean4TransferObject.getMyStrings()[1]);
		assertEquals("third", myBean4TransferObject.getMyStrings()[2]);
		assertEquals(55, myBean4TransferObject.getLength());
	}	
	
	private static class Bean4ToBean4TransferObject extends AbstractSimpleBeanConverter<MyBean4, MyBean4TransferObject>{

		protected Bean4ToBean4TransferObject() {
			super(MyBean4.class, MyBean4TransferObject.class);
		}

		@Override
		public MyBean4TransferObject doConvert(ConversionContext context,
				MyBean4 sourceObject, TypeReference<?> destinationType)
				throws ConverterException {
			MyBean4TransferObject result = new MyBean4TransferObject();
			result.setLength(sourceObject.getSize());
			result.setMyString(sourceObject.getMyString());
			result.setMyStrings(convertElement(context, sourceObject.getMyStrings(), String[].class));
			
			return result;
		}
		
	}
	
}
