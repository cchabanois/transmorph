package net.entropysoft.transmorph.signature.formatter;

import java.util.Map;

import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import junit.framework.TestCase;

public class TypeSignatureFormatterTest extends TestCase {

	public void testFormatPrimitive() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();
		
		assertEquals("I",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(Integer.TYPE)));
	}
	
	public void testFormatClass() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();
		
		assertEquals("Ljava/lang/String;",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(String.class)));
	}
	
	public void testFormatArray() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();
		
		assertEquals("[[Ljava/lang/String;",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(String[][].class)));
		assertEquals("[[I",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(int[][].class)));
	}
	
	public void testFormatWithGenerics() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();
		
		assertEquals("Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;",typeSignatureFormatter.format(TypeSignatureFactory.getTypeSignature(Map.class, new Class[] { String.class, Integer.class } )));
		
	}
	
}
