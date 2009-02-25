package net.entropysoft.transmorph.signature.formatter;

import java.util.Map;

import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import junit.framework.TestCase;

public class TypeSignatureFormatterTest extends TestCase {

	public void testFormatPrimitive() throws Exception {
		TypeSignatureFormatter typeSignatureFormatter = new TypeSignatureFormatter();
		
		assertEquals("I",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(Integer.TYPE)));
	}
	
	public void testFormatClass() throws Exception {
		TypeSignatureFormatter typeSignatureFormatter = new TypeSignatureFormatter();
		
		assertEquals("Ljava/lang/String;",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(String.class)));
	}
	
	public void testFormatArray() throws Exception {
		TypeSignatureFormatter typeSignatureFormatter = new TypeSignatureFormatter();
		
		assertEquals("[[Ljava/lang/String;",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(String[][].class)));
		assertEquals("[[I",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(int[][].class)));
	}
	
	public void testFormatWithGenerics() throws Exception {
		TypeSignatureFormatter typeSignatureFormatter = new TypeSignatureFormatter();
		
		assertEquals("Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(Map.class, new Class[] { String.class, Integer.class } )));
		
	}
	
}
