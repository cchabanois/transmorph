package net.entropysoft.transmorph.signature;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureParser;

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
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser("Ljava/util/List<*>;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
	}
}
