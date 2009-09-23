package net.entropysoft.transmorph.signature.parser;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.FullTypeSignature;

public class JavaTypeSignatureParserTest extends TestCase {

	public void testParsePrimitive() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"long");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("J", typeSignature.getSignature());
	}

	public void testParseArrayOfPrimitives() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"long[][]");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("[[J", typeSignature.getSignature());
	}

	public void testParseClassName() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"String");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/lang/String;", typeSignature.getSignature());
	}

	public void testParseArrayOfClasses() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"java.lang.String[][][]");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("[[[Ljava/lang/String;", typeSignature.getSignature());
	}

	public void testParseWithGenerics() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"java.util.Map<java.lang.String, java.util.List<java.lang.String>>");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	public void testParseGenericsWildcard() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"java.util.List<?>");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/util/List<*>;", typeSignature.getSignature());

		typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"java.util.List<? extends java.lang.Number>");
		typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/util/List<+Ljava/lang/Number;>;", typeSignature
				.getSignature());
	}

	public void testImports() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"List<String>");
		ImportedClassesProvider importedClassesProvider = new ImportedClassesProvider();
		importedClassesProvider.importClass("java.util.List");
		typeSignatureParser.setImportedClassesProviders(new JavaLangImportedClassesProvider(),importedClassesProvider);
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/util/List<Ljava/lang/String;>;", typeSignature
				.getSignature());
	}

}
