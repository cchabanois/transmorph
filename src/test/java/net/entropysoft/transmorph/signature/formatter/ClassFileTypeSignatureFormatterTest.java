package net.entropysoft.transmorph.signature.formatter;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

public class ClassFileTypeSignatureFormatterTest {

	@Test
	public void testFormatPrimitive() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();

		assertEquals("I", typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(Integer.TYPE)));
	}

	@Test
	public void testFormatClass() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();

		assertEquals("Ljava/lang/String;", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));
		typeSignatureFormatter.setUseInternalFormFullyQualifiedName(false);
		assertEquals("Ljava.lang.String;", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));
	}

	@Test
	public void testFormatArray() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();

		assertEquals("[[Ljava/lang/String;",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(String[][].class)));
		assertEquals("[[I", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(int[][].class)));

		typeSignatureFormatter.setUseInternalFormFullyQualifiedName(false);
		assertEquals("[[Ljava.lang.String;",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(String[][].class)));

	}

	@Test
	public void testFormatWithGenerics() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();

		assertEquals("Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;",
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(Map.class, new Class[] {
								String.class, Integer.class })));
		assertEquals("Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(Map.class,
						new Class[] { String.class, Integer.class })));

		assertEquals("Ljava/util/List<+Ljava/lang/Integer;>;", typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(
						"Ljava.util.List<+Ljava.lang.Integer;>;", false)));
	}

	@Test
	public void testFormatWildcardType() throws Exception {
		ClassFileTypeSignatureFormatter typeSignatureFormatter = new ClassFileTypeSignatureFormatter();
		TypeReference<List<? extends Number>> typeReference = new TypeReference<List<? extends Number>>() {
		};
		TypeReference<?> wildardTypeRef = typeReference.getTypeArguments()[0];
		assertEquals("+Ljava/lang/Number;", typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(wildardTypeRef.getType())));
	}

}
