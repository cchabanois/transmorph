/**
 * 
 */
package samples;

import java.util.List;

public class MyBean1 {
	private int myInt;
	private List<String> myListOfStrings;
	private MyBean2 myBean2;
	private MyBean3 myBean3;

	public int getMyInt() {
		return myInt;
	}

	public void setMyInt(int myInt) {
		this.myInt = myInt;
	}

	public List<String> getMyListOfStrings() {
		return myListOfStrings;
	}

	public void setMyListOfStrings(List<String> myListOfStrings) {
		this.myListOfStrings = myListOfStrings;
	}

	public MyBean2 getMyBean2() {
		return myBean2;
	}

	public void setMyBean2(MyBean2 myBean2) {
		this.myBean2 = myBean2;
	}

	public MyBean3 getMyBean3() {
		return myBean3;
	}

	public void setMyBean3(MyBean3 myBean3) {
		this.myBean3 = myBean3;
	}

}