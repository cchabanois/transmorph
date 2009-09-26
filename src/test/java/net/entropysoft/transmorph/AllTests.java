package net.entropysoft.transmorph;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { net.entropysoft.transmorph.converters.AllTests.class,
		net.entropysoft.transmorph.injectors.AllTests.class,
		net.entropysoft.transmorph.signature.AllTests.class,
		net.entropysoft.transmorph.type.AllTests.class,
		net.entropysoft.transmorph.utils.AllTests.class,
		DefaultFromStringConvertersTest.class,
		DefaultToStringConvertersTest.class, TransmorphTest.class })
public class AllTests {

}
