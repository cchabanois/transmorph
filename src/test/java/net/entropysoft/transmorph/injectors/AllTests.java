package net.entropysoft.transmorph.injectors;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { BeanToBeanInjectorTest.class, MapToBeanTest.class })
public class AllTests {

}
