package net.entropysoft.transmorph.signature.parser;

import static org.junit.Assert.assertEquals;
import net.entropysoft.transmorph.signature.FullTypeSignature;

import org.junit.Test;

import samples.BeanWithInnerBean;

public class ClassGetNameTypeSignatureParserTest {

	@Test
	public void testClassGetNameTypeSignatureParser() throws Exception {
		ClassGetNameTypeSignatureParser typeSignatureParser = new ClassGetNameTypeSignatureParser();
		typeSignatureParser.setTypeSignature(String.class.getName());
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/lang/String;", typeSignature.getSignature());
		
		typeSignatureParser.setTypeSignature("int");
		typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("I", typeSignature.getSignature());
		
		typeSignatureParser.setTypeSignature(String[].class.getName());
		typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("[Ljava/lang/String;", typeSignature.getSignature());
		
		typeSignatureParser.setTypeSignature(BeanWithInnerBean.InnerBean.class.getName());
		typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Lsamples/BeanWithInnerBean.InnerBean;", typeSignature.getSignature());
	}
	
}
