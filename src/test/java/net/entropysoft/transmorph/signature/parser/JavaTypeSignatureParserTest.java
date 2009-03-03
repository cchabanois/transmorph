package net.entropysoft.transmorph.signature.parser;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.TypeSignature;

public class JavaTypeSignatureParserTest extends TestCase {

	public void testParsePrimitive() {
		JavaTypeSignatureParser typeSignatureParser = new JavaTypeSignatureParser(
				"long");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("J", typeSignature.getSignature());
	}

	public void testParseArrayOfPrimitives() {
		JavaTypeSignatureParser typeSignatureParser = new JavaTypeSignatureParser(
				"long[][]");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("[[J", typeSignature.getSignature());
	}

	public void testParseClassName() {
		JavaTypeSignatureParser typeSignatureParser = new JavaTypeSignatureParser(
				"String");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/lang/String;", typeSignature.getSignature());
	}

	public void testParseArrayOfClasses() {
		JavaTypeSignatureParser typeSignatureParser = new JavaTypeSignatureParser(
				"java.lang.String[][][]");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("[[[Ljava/lang/String;", typeSignature.getSignature());
	}

	public void testParseWithGenerics() {
		JavaTypeSignatureParser typeSignatureParser = new JavaTypeSignatureParser(
				"java.util.Map<java.lang.String, java.util.List<java.lang.String>>");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	public void testParseGenericsWildcard() {
		JavaTypeSignatureParser typeSignatureParser = new JavaTypeSignatureParser(
				"java.util.List<?>");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/util/List<*>;", typeSignature.getSignature());

		typeSignatureParser = new JavaTypeSignatureParser(
				"java.util.List<? extends java.lang.Number>");
		typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/util/List<+Ljava/lang/Number;>;", typeSignature
				.getSignature());
	}

	public void testImports() {
		JavaTypeSignatureParser typeSignatureParser = new JavaTypeSignatureParser(
				"List<String>");
		ImportedClassesProvider importedClassesProvider = new ImportedClassesProvider();
		importedClassesProvider.importClass("java.util.List");
		typeSignatureParser.setImportedClassesProviders(new JavaLangImportedClassesProvider(),importedClassesProvider);
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/util/List<Ljava/lang/String;>;", typeSignature
				.getSignature());
	}

}
