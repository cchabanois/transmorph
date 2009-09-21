package net.entropysoft.transmorph.signature;

import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

public class JavaTypeToTypeSignatureTest extends TestCase {

	public void testGenericJavaType() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithGenericParameter", List.class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<Ljava/lang/String;>;", typeSignature.getSignature());
	}

	public void testNonGenericJavaType() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithNonGenericParameter", List.class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List;", typeSignature.getSignature());
	}
	
	public void testGenericArray() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithGenericArray", List[].class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("[Ljava/util/List<Ljava/lang/String;>;", typeSignature.getSignature());		
	}

	public void testNonGenericArray() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithNonGenericArray", int[].class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("[I", typeSignature.getSignature());		
	}
	
	public void testUnboundedWildcard() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithUnboundedWildcard", List.class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<*Ljava/lang/Object;>;", typeSignature.getSignature());
	}
	
	public void testExtendsWildcard() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithExtendsWildcard", List.class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<+Ljava/lang/Number;>;", typeSignature.getSignature());
	}
	
	public void testSuperWildcard() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithSuperWildcard", List.class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<-Ljava/lang/Integer;>;", typeSignature.getSignature());
	}	
	
	public void testInnerClass() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithInnerClass", InnerClass.class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Lnet/entropysoft/transmorph/signature/JavaTypeToTypeSignatureTest.InnerClass<Ljava/lang/Integer;>;", typeSignature.getSignature());
	}
	
	public void testInnerInnerClass() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithInnerInnerClass", InnerClass.InnerInnerClass.class);
		Signature typeSignature = javaTypeToTypeSignature.getSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Lnet/entropysoft/transmorph/signature/JavaTypeToTypeSignatureTest.InnerClass.InnerInnerClass<Ljava/lang/String;>;", typeSignature.getSignature());
	}
	
	public void methodWithGenericParameter(List<String> listOfStrings) {
		
	}
	
	public void methodWithNonGenericParameter(List list) {
		
	}
	
	public void methodWithGenericArray(List<String>[] array) {
		
	}
	
	public void methodWithNonGenericArray(int[] array) {
		
	}
	
	public void methodWithUnboundedWildcard(List<?> list) {
		
	}
	
	public void methodWithExtendsWildcard(List<? extends Number> list) {
		
	}
	
	public void methodWithSuperWildcard(List<? super Integer> list) {
		
	}
	
	public void methodWithInnerClass(InnerClass<Integer> innerClass) {
		
	}
	
	public void methodWithInnerInnerClass(InnerClass.InnerInnerClass<String> innerClass) {
		
	}
	
	public static class InnerClass<E> {
		
		public static class InnerInnerClass<F> {
		
		}
		
	}
	
}
