package net.entropysoft.transmorph.signature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

public class JavaTypeToTypeSignatureTest {

	@Test
	public void testGenericJavaType() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithGenericParameter", List.class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<Ljava/lang/String;>;", typeSignature.getSignature());
	}

	@Test
	public void testNonGenericJavaType() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithNonGenericParameter", List.class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List;", typeSignature.getSignature());
	}
	
	@Test
	public void testGenericArray() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithGenericArray", List[].class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("[Ljava/util/List<Ljava/lang/String;>;", typeSignature.getSignature());		
	}

	@Test
	public void testNonGenericArray() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithNonGenericArray", int[].class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("[I", typeSignature.getSignature());		
	}
	
	@Test
	public void testUnboundedWildcard() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithUnboundedWildcard", List.class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<*Ljava/lang/Object;>;", typeSignature.getSignature());
	}
	
	@Test
	public void testExtendsWildcard() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithExtendsWildcard", List.class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<+Ljava/lang/Number;>;", typeSignature.getSignature());
	}
	
	@Test
	public void testSuperWildcard() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithSuperWildcard", List.class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Ljava/util/List<-Ljava/lang/Integer;>;", typeSignature.getSignature());
	}	
	
	@Test
	public void testInnerClass() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithInnerClass", InnerClass.class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
		assertNotNull(typeSignature);
		assertEquals("Lnet/entropysoft/transmorph/signature/JavaTypeToTypeSignatureTest.InnerClass<Ljava/lang/Integer;>;", typeSignature.getSignature());
	}
	
	@Test
	public void testInnerInnerClass() throws Exception {
		JavaTypeToTypeSignature javaTypeToTypeSignature = new JavaTypeToTypeSignature();
		Method method = JavaTypeToTypeSignatureTest.class.getMethod("methodWithInnerInnerClass", InnerClass.InnerInnerClass.class);
		TypeSignature typeSignature = javaTypeToTypeSignature.getTypeSignature(method.getGenericParameterTypes()[0]);
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
