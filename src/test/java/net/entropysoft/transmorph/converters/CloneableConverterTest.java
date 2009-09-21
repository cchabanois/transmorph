package net.entropysoft.transmorph.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import junit.framework.TestCase;
import net.entropysoft.transmorph.Transmorph;
import samples.CloneableBean;

public class CloneableConverterTest extends TestCase {

	public void testIdentity() throws Exception {
		Transmorph converter = new Transmorph(new CloneableConverter());

		CloneableBean cloneableBean = new CloneableBean();
		cloneableBean.setMyDates(new ArrayList<Date>(Arrays.asList(new Date(0),
				new Date(1))));
		cloneableBean.setMyString("Hello world");

		CloneableBean clone = converter.convert(cloneableBean,
				CloneableBean.class);
		assertNotSame(cloneableBean, clone);
		assertEquals(new Date(0), clone.getMyDates().get(0));
		assertEquals(new Date(1), clone.getMyDates().get(1));
		assertEquals("Hello world", cloneableBean.getMyString());
	}

}
