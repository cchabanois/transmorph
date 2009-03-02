package net.entropysoft.transmorph.signature;

import java.util.Map;

import net.entropysoft.transmorph.signature.ClassFactory;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.parser.ClassFileTypeSignatureParser;

import junit.framework.TestCase;

public class ClassFactoryTest extends TestCase {

	public void testClassFactoryWithClass() throws Exception {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser("Ljava/util/Map<Ljava/lang/String;Ljava/util/List<Ljava/lang/String;>;>;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		assertEquals(Map.class, classFactory.getClass(typeSignature));
	}
	
	public void testClassFactoryInvalidFormError() throws Exception {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser("[Ljava.lang.String;");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		try {
			classFactory.getClass(typeSignature);
			fail("Should not be able to get class");
		} catch (ClassNotFoundException e) {
			
		}
	}
	
	public void testClassFactoryWithArray() throws Exception {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser("[[I");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		assertEquals((new int[][] {}).getClass(), classFactory.getClass(typeSignature));
	}
	
	public void testClassFactoryWithPrimitive() throws Exception {
		ClassFileTypeSignatureParser typeSignatureParser = new ClassFileTypeSignatureParser("I");
		TypeSignature typeSignature = typeSignatureParser.parseTypeSignature();
		ClassFactory classFactory = new ClassFactory(Thread.currentThread().getContextClassLoader());
		assertEquals(Integer.TYPE, classFactory.getClass(typeSignature));
	}
	
}
