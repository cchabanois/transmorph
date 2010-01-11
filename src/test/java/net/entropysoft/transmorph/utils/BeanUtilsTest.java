package net.entropysoft.transmorph.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Test;


public class BeanUtilsTest {

	@Test
	public void testGetSetters() throws Exception {
		Map<String, Method> setters = BeanUtils.getSetters(Bean.class);
		assertEquals(2, setters.size());
		assertNotNull(setters.get("myString"));
		assertNotNull(setters.get("myBoolean"));
	}
	
	@Test
	public void testGetGetters() throws Exception {
		Map<String, Method> getters = BeanUtils.getGetters(Bean.class);
		assertEquals(3, getters.size());
		assertNotNull(getters.get("class"));
		assertNotNull(getters.get("myString"));
		assertNotNull(getters.get("myBoolean"));
	}
	
	
	private class Ancestor {
		private String myString;
		private String myPrivateString;
		
		public String getMyString() {
			return myString;
		}
		
		public void setMyString(String myString) {
			this.myString = myString;
		}
		
		protected String getMyPrivateString() {
			return myPrivateString;
		}
		
		protected void setMyPrivateString(String myPrivateString) {
			this.myPrivateString = myPrivateString;
		}
		
	}
	
	private class Bean extends Ancestor {
		private boolean myBoolean;

		public boolean isMyBoolean() {
			return myBoolean;
		}
		
		public void setMyBoolean(boolean myBoolean) {
			this.myBoolean = myBoolean;
		}
		
	}
	
}
