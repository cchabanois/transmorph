package samples;

import java.io.Serializable;
import java.util.List;

public class SerializableBean implements Serializable {

	private String myString;
	private List<String> myListOfStrings;
	
	public SerializableBean() {
		
	}
	
	public void setMyListOfStrings(List<String> myListOfStrings) {
		this.myListOfStrings = myListOfStrings;
	}
	
	public List<String> getMyListOfStrings() {
		return myListOfStrings;
	}
	
	public void setMyString(String myString) {
		this.myString = myString;
	}

	public String getMyString() {
		return myString;
	}
	
}
