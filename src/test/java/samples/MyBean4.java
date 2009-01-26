/**
 * 
 */
package samples;

import java.util.List;


public class MyBean4 extends MyBean4Ancestor {
	private String myString;
	private List<String> myStrings;

	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}

	public List<String> getMyStrings() {
		return myStrings;
	}

	public void setMyStrings(List<String> myStrings) {
		this.myStrings = myStrings;
	}
}