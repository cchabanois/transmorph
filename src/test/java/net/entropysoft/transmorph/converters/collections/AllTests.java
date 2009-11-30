package net.entropysoft.transmorph.converters.collections;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = { ArrayToStringTest.class, MapToStringTest.class,
		ArrayToCollectionTest.class, ArrayToSingleElementTest.class,
		CollectionToArrayTest.class, CollectionToStringTest.class,
		MapToMapTest.class, CollectionToCollectionTest.class,
		CollectionToSingleElementTest.class, ArrayToArrayTest.class })
public class AllTests {

}
