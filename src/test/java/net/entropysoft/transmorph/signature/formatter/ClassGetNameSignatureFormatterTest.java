package net.entropysoft.transmorph.signature.formatter;

import java.util.Map;

import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import junit.framework.TestCase;

public class ClassGetNameSignatureFormatterTest extends TestCase {

	public void testFormatPrimitive() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(Integer.TYPE.getName(), typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(Integer.TYPE)));
	}

	public void testFormatClass() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(String.class.getName(), typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));
	}

	public void testFormatArray() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(String[][].class.getName(),
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(String[][].class)));
		assertEquals(int[][].class.getName(), typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(int[][].class)));
	}

	public void testFormatWithGenerics() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(Map.class.getName(),
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(Map.class, new Class[] {
								String.class, Integer.class })));

	}

}
