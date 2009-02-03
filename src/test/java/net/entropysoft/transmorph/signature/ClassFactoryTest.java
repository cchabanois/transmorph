package net.entropysoft.transmorph.signature;

import java.util.Map;

import net.entropysoft.transmorph.signature.ClassFactory;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureParser;

import junit.framework.TestCase;

public class ClassFactoryTest extends TestCase {

	public void testClassFactoryWithClass() throws Exception {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser("Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		assertEquals(Map.class, classFactory.getClass(typeSignature));
	}
	
	public void testClassFactoryInvalidFormError() throws Exception {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser("[Ljava.lang.String;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		try {
			classFactory.getClass(typeSignature);
			fail("Should not be able to get class");
		} catch (ClassNotFoundException e) {
			
		}
	}
	
	public void testClassFactoryWithArray() throws Exception {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser("[[I;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		assertEquals((new int[][] {}).getClass(), classFactory.getClass(typeSignature));
	}
	
	public void testClassFactoryWithPrimitive() throws Exception {
		TypeSignatureParser typeSignatureParser = new TypeSignatureParser("I");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		assertEquals(Integer.TYPE, classFactory.getClass(typeSignature));
	}
	
}
