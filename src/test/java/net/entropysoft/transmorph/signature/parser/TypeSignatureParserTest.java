package net.entropysoft.transmorph.signature.parser;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.parser.InvalidSignatureException;
import net.entropysoft.transmorph.signature.parser.TypeSignatureParser;

public class TypeSignatureParserTest extends TestCase {

	public void testParse() {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	public void testParseGenericsWildcard() {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser(
				"Ljava/util/List<*>;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
	}

	public void testParseNotUsingInternalFQN() {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
		typeSignatureParser.setUseInternalFormFullyQualifiedName(false);
		try {
			typeSignatureParser.parseTypeSignature();
			fail("Should not be able to parse");
		} catch (InvalidSignatureException e) {

		}
		typeSignatureParser = new TypeSignatureParser(
				"Ljava.util.Map<Ljava.lang.String;Ljava.util.List<Ljava.lang.String;>;>;");
		typeSignatureParser.setUseInternalFormFullyQualifiedName(false);
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	public void testParseWithExcessiveChars() {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;ABC");
		try {
			TypeSignature typeSignature = typeSignatureParser
					.parseTypeSignature();
			fail("Should not have been able to parse");
		} catch (InvalidSignatureException e) {

		}
	}

}
