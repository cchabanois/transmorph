package net.entropysoft.transmorph.signature.parser;

import static org.junit.Assert.assertEquals;
import net.entropysoft.transmorph.signature.FullTypeSignature;

import org.junit.Test;

public class JavaTypeSignatureParserTest {

	@Test
	public void testParsePrimitive() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"long");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("J", typeSignature.getSignature());
	}

	@Test
	public void testParseArrayOfPrimitives() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"long[][]");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("[[J", typeSignature.getSignature());
	}

	@Test
	public void testParseClassName() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"String");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("Ljava/lang/String;", typeSignature.getSignature());
	}

	@Test
	public void testParseArrayOfClasses() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"java.lang.String[][][]");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals("[[[Ljava/lang/String;", typeSignature.getSignature());
	}

	@Test
	public void testParseWithGenerics() {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser(
				"java.util.Map<java.lang.String, java.util.List<java.lang.String>>");
		FullTypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(
				"Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;",
				typeSignature.getSignature());
	}

	@Test
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

	@Test
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
