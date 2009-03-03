/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.entropysoft.transmorph.signature.parser;

/**
 * Provides classes imported by default (classes that are from java.lang)
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public class JavaLangImportedClassesProvider extends AbstractImportedClassesProvider {
	
	public JavaLangImportedClassesProvider() {
		importJavaLangClasses();
	}

	private void importJavaLangClasses() {
		importClass("java.lang.Appendable");
		importClass("java.lang.CharSequence");
		importClass("java.lang.Cloneable");
		importClass("java.lang.Comparable");
		importClass("java.lang.Iterable");
		importClass("java.lang.Readable");
		importClass("java.lang.Runnable");
		importClass("java.lang.Boolean");
		importClass("java.lang.Byte");
		importClass("java.lang.Character");
		importClass("java.lang.Class");
		importClass("java.lang.ClassLoader");
		importClass("java.lang.Compiler");
		importClass("java.lang.Double");
		importClass("java.lang.Enum");
		importClass("java.lang.Float");
		importClass("java.lang.InheritableThreadLocal");
		importClass("java.lang.Integer");
		importClass("java.lang.Long");
		importClass("java.lang.Math");
		importClass("java.lang.Number");
		importClass("java.lang.Object");
		importClass("java.lang.Package");
		importClass("java.lang.Process");
		importClass("java.lang.ProcessBuilder");
		importClass("java.lang.Runtime");
		importClass("java.lang.RuntimePermission");
		importClass("java.lang.SecurityManager");
		importClass("java.lang.Short");
		importClass("java.lang.StackTraceElement");
		importClass("java.lang.StrictMath");
		importClass("java.lang.String");
		importClass("java.lang.StringBuffer");
		importClass("java.lang.StringBuilder");
		importClass("java.lang.System");
		importClass("java.lang.Thread");
		importClass("java.lang.ThreadGroup");
		importClass("java.lang.ThreadLocal");
		importClass("java.lang.Throwable");
		importClass("java.lang.Void");
		importClass("java.lang.ArithmeticException");
		importClass("java.lang.ArrayIndexOutOfBoundsException");
		importClass("java.lang.ArrayStoreException");
		importClass("java.lang.ClassCastException");
		importClass("java.lang.ClassNotFoundException");
		importClass("java.lang.CloneNotSupportedException");
		importClass("java.lang.EnumConstantNotPresentException");
		importClass("java.lang.Exception");
		importClass("java.lang.IllegalAccessException");
		importClass("java.lang.IllegalArgumentException");
		importClass("java.lang.IllegalMonitorStateException");
		importClass("java.lang.IllegalStateException");
		importClass("java.lang.IllegalThreadStateException");
		importClass("java.lang.IndexOutOfBoundsException");
		importClass("java.lang.InstantiationException");
		importClass("java.lang.InterruptedException");
		importClass("java.lang.NegativeArraySizeException");
		importClass("java.lang.NoSuchFieldException");
		importClass("java.lang.NoSuchMethodException");
		importClass("java.lang.NullPointerException");
		importClass("java.lang.NumberFormatException");
		importClass("java.lang.RuntimeException");
		importClass("java.lang.SecurityException");
		importClass("java.lang.StringIndexOutOfBoundsException");
		importClass("java.lang.TypeNotPresentException");
		importClass("java.lang.UnsupportedOperationException");
		importClass("java.lang.AbstractMethodError");
		importClass("java.lang.AssertionError");
		importClass("java.lang.ClassCircularityError");
		importClass("java.lang.ClassFormatError");
		importClass("java.lang.Error");
		importClass("java.lang.ExceptionInInitializerError");
		importClass("java.lang.IllegalAccessError");
		importClass("java.lang.IncompatibleClassChangeError");
		importClass("java.lang.InstantiationError");
		importClass("java.lang.InternalError");
		importClass("java.lang.LinkageError");
		importClass("java.lang.NoClassDefFoundError");
		importClass("java.lang.NoSuchFieldError");
		importClass("java.lang.NoSuchMethodError");
		importClass("java.lang.OutOfMemoryError");
		importClass("java.lang.StackOverflowError");
		importClass("java.lang.ThreadDeath");
		importClass("java.lang.UnknownError");
		importClass("java.lang.UnsatisfiedLinkError");
		importClass("java.lang.UnsupportedClassVersionError");
		importClass("java.lang.VerifyError");
		importClass("java.lang.VirtualMachineError");
	}
	
	
}
