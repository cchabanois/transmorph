package net.entropysoft.transmorph.signature.formatter;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

public class JavaSyntaxTypeSignatureFormatterTest {

	@Test
	public void testFormatPrimitive() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();

		assertEquals("int", typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(Integer.TYPE)));
	}

	@Test
	public void testFormatClass() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();

		assertEquals("java.lang.String", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));

		typeSignatureFormatter.setUseSimpleNames(true);
		assertEquals("String", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));
	}

	@Test
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

	@Test
	public void testFormatWithGenerics() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();

		assertEquals("java.util.Map<java.lang.String,java.lang.Integer>",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(Map.class, new Class[] {
								String.class, Integer.class })));
		typeSignatureFormatter.setUseSimpleNames(true);
		assertEquals("Map<String,Integer>", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(Map.class,
						new Class[] { String.class, Integer.class })));

		assertEquals("List<? extends Integer>", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(
						"Ljava.util.List<+Ljava.lang.Integer;>;", false)));
	}

	@Test
	public void testFormatWildcardType() throws Exception {
		JavaSyntaxTypeSignatureFormatter typeSignatureFormatter = new JavaSyntaxTypeSignatureFormatter();
		TypeReference<List<? extends Number>> typeReference = new TypeReference<List<? extends Number>>() {
		};
		TypeReference<?> wildardTypeRef = typeReference.getTypeArguments()[0];
		assertEquals("? extends java.lang.Number", typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(wildardTypeRef.getType())));
	}

}
