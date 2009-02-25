package net.entropysoft.transmorph.signature.formatter;

import java.util.Map;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;

public class JavaSyntaxTypeSignatureFormatterTest extends TestCase {

	public void testFormatPrimitive() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();

		assertEquals("int", typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(Integer.TYPE)));
	}

	public void testFormatClass() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();

		assertEquals("java.lang.String", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));
		
		typeSignatureFormatter.setUseSimpleNames(true);
		assertEquals("String", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));
	}

	public void testFormatArray() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();

		assertEquals("java.lang.String[][]",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(String[][].class)));
		assertEquals("int[][]", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(int[][].class)));
		
		typeSignatureFormatter.setUseSimpleNames(true);
		assertEquals("String[][]",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(String[][].class)));

	}

	public void testFormatWithGenerics() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();

		assertEquals("java.util.Map<java.lang.String,java.lang.Integer>",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(Map.class, new Class[] {
								String.class, Integer.class })));
		typeSignatureFormatter.setUseSimpleNames(true);
		assertEquals("Map<String,Integer>",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(Map.class, new Class[] {
								String.class, Integer.class })));
		
		assertEquals("List<? extends Integer>",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature("Ljava.util.List<+Ljava.lang.Integer;>;", false)));
	}
}
