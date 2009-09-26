package net.entropysoft.transmorph.signature;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { TypeFactoryTest.class,
		JavaTypeToTypeSignatureTest.class, TypeSignatureFactoryTest.class,
		net.entropysoft.transmorph.signature.formatter.AllTests.class,
		net.entropysoft.transmorph.signature.parser.AllTests.class })
public class AllTests {

}
