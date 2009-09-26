package net.entropysoft.transmorph.signature.formatter;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import net.entropysoft.transmorph.signature.TypeSignatureFactory;
import net.entropysoft.transmorph.type.TypeReference;

import org.junit.Test;

public class ClassGetNameSignatureFormatterTest {

	@Test
	public void testFormatPrimitive() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(Integer.TYPE.getName(), typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(Integer.TYPE)));
	}

	@Test
	public void testFormatClass() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(String.class.getName(), typeSignatureFormatter
				.format(TypeSignatureFactory.getTypeSignature(String.class)));
	}

	@Test
	public void testFormatArray() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(String[][].class.getName(),
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(String[][].class)));
		assertEquals(int[][].class.getName(), typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(int[][].class)));
	}

	@Test
	public void testFormatWithGenerics() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();

		assertEquals(Map.class.getName(),
				typeSignatureFormatter.format(TypeSignatureFactory
						.getTypeSignature(Map.class, new Class[] {
								String.class, Integer.class })));

	}

	@Test
	public void testFormatWildcardType() throws Exception {
		ClassGetNameSignatureFormatter typeSignatureFormatter = new ClassGetNameSignatureFormatter();
		TypeReference<List<? extends Number>> typeReference = new TypeReference<List<? extends Number>>() {
		};
		TypeReference<?> wildardTypeRef = typeReference.getTypeArguments()[0];
		assertEquals("java.lang.Number", typeSignatureFormatter.format(TypeSignatureFactory
				.getTypeSignature(wildardTypeRef.getType())));
	}	
	
}
