package net.entropysoft.transmorph.signature;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.entropysoft.transmorph.signature.parser.ClassFileTypeSignatureParser;
import net.entropysoft.transmorph.signature.parser.JavaSyntaxTypeSignatureParser;
import net.entropysoft.transmorph.type.TypeReference;

public class TypeFactoryTest extends TestCase {

	private TypeFactory typeFactory;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		typeFactory = new TypeFactory(getClass().getClassLoader());
	}
	
	public void testPrimitive() throws Exception {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser("I");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(Integer.TYPE, typeFactory.getType(typeSignature));
	}
	
	public void testClass() throws Exception {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser("Ljava.lang.String;");
		typeSignatureParser.setUseInternalFormFullyQualifiedName(false);
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		assertEquals(String.class, typeFactory.getType(typeSignature));
	}
	
	public void testParameterizedClass() throws Exception {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser("java.util.Map<java.lang.String, java.util.List<java.lang.String>>");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		TypeReference<Map<String,List<String>>> typeReference = new TypeReference<Map<String,List<String>>>() {
		};
		assertEquals(typeReference.getType(), typeFactory.getType(typeSignature));
	}
	
	public void testParameterizedInnerClass() throws Exception {
		JavaSyntaxTypeSignatureParser typeSignatureParser = new JavaSyntaxTypeSignatureParser("net.entropysoft.transmorph.signature.TypeFactoryTest$InnerClass<java.lang.String>");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		TypeReference<InnerClass<String>> typeReference = new TypeReference<InnerClass<String>>() {
		};
		assertEquals(typeReference.getType(), typeFactory.getType(typeSignature));
	}
	
	private static class InnerClass<T> {
		
	}
	
}
