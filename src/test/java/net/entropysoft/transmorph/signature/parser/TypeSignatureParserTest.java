package net.entropysoft.transmorph.signature.parser;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.TypeSignature;

public class TypeSignatureParserTest extends TestCase {

	public void testParse() {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	public void testParseGenericsWildcard() {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava/util/List<*>;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
	}

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
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	public void testParseWithExcessiveChars() {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;ABC");
		try {
			TypeSignature typeSignature = typeSignatureParser
					.parseTypeSignature();
			fail("Should not have been able to parse");
		} catch (InvalidSignatureException e) {

		}
	}

}
