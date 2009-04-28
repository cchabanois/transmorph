package net.entropysoft.transmorph;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import samples.MyBean1;

public class BeanInjectorTest extends TestCase {

	public void testBeanInjector() throws Exception {
		MyBean1 myBean1 = new MyBean1();
		BeanInjector beanInjector = new BeanInjector(myBean1, new Transmorph(new DefaultConverters()));
		Map<String,Object> properties = new HashMap<String, Object>();
		properties.put("myInt", "55");
		beanInjector.inject(properties);
		assertEquals(55, myBean1.getMyInt());
	}
	
	
	
}
