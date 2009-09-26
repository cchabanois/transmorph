package net.entropysoft.transmorph.signature.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.entropysoft.transmorph.signature.FullTypeSignature;

import org.junit.Test;

public class ClassFileTypeSignatureParserTest {

	@Test
	public void testParse() {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	@Test
	public void testParseGenericsWildcard() {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava/util/List<*>;");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
	}

	@Test
	public void testParseNotUsingInternalFQN() {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
		typeSignatureParser.setUseInternalFormFullyQualifiedName(false);
		try {
			typeSignatureParser.parseTypeSignature();
			fail("Should not be able to parse");
		} catch (InvalidSignatureException e) {

		}
		typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava.util.Map<Ljava.lang.String;Ljava.util.List<Ljava.lang.String;>;>;");
		typeSignatureParser.setUseInternalFormFullyQualifiedName(false);
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	@Test
	public void testParseWithExcessiveChars() {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;ABC");
		try {
			FullTypeSignature typeSignature = typeSignatureParser
					.parseTypeSignature();
			fail("Should not have been able to parse");
		} catch (InvalidSignatureException e) {

		}
	}

}
