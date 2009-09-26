package net.entropysoft.transmorph.signature.formatter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { ClassGetNameSignatureFormatterTest.class,
		ClassFileTypeSignatureFormatterTest.class,
		JavaSyntaxTypeSignatureFormatterTest.class })
public class AllTests {

}
