package net.entropysoft.transmorph.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { BeanUtilsTest.class, NumberComparatorTest.class, NumberInRangeTest.class,
		NumberUtilsTest.class,TypeReferenceTest.class })
public class AllTests {

}
