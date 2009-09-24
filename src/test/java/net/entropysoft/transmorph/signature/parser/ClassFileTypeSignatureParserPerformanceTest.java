package net.entropysoft.transmorph.signature.parser;

import java.text.MessageFormat;

import junit.framework.TestCase;

import net.entropysoft.transmorph.signature.JavaTypeToTypeSignature;
import net.entropysoft.transmorph.signature.TypeSignature;
import net.entropysoft.transmorph.signature.TypeSignatureFactory;

public class ClassFileTypeSignatureParserPerformanceTest extends TestCase {

	public void testClassFileTypeSignatureParserPerformance() throws Exception {
		runParser("Ljava/lang/String;",
				1000000, 0);
		
	}
	
	private void runParser(String typeSignatureString,
			long numIterations, long maxTimeAllowedPerIteration) throws Exception {
		// warm up
		TypeSignature typeSignature = TypeSignatureFactory.getTypeSignature(typeSignatureString);
		if (typeSignature.getClass().hashCode() == System.nanoTime())
			System.out.print(' ');
		
		long startTime = System.nanoTime();

		for (int i = 0; i < numIterations; i++) {
			typeSignature = TypeSignatureFactory.getTypeSignature(typeSignatureString);
			if (typeSignature.getClass().hashCode() == System.nanoTime())
				System.out.print(' ');
		}
		long stopTime = System.nanoTime();

		long elapsedMsPerIteration = (stopTime - startTime)
				/ (1000000 * numIterations);

		if (elapsedMsPerIteration > maxTimeAllowedPerIteration) {
			fail(MessageFormat
					.format(
							"{0} took {1}ms while max allowed time was {2}ms.",
							typeSignatureString, elapsedMsPerIteration,
							maxTimeAllowedPerIteration));
		} else {
			System.out.println(MessageFormat.format(
					"{0} took {1}ms (max allowed time was {2}ms)", typeSignatureString,
					elapsedMsPerIteration, maxTimeAllowedPerIteration));
		}
	}

	
}
