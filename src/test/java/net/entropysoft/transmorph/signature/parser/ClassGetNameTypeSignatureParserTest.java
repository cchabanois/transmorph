package net.entropysoft.transmorph.signature.parser;

import samples.BeanWithInnerBean;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.parser.ClassGetNameTypeSignatureParser;
import junit.framework.TestCase;

public class ClassGetNameTypeSignatureParserTest extends TestCase {

	public void testClassGetNameTypeSignatureParser() throws Exception {
		ClassGetNameTypeSignatureParser typeSignatureParser = new ClassGetNameTypeSignatureParser();
		typeSignatureParser.setTypeSignature(String.class.getName());
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
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
