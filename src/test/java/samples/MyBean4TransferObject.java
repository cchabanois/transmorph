/**
 * 
 */
package samples;

public class MyBean4TransferObject implements java.io.Serializable {

	private static final long serialVersionUID = 4859101895339173273L;

	private String myString;
	private String[] myStrings;
	private long length;

	public String getMyString() {
		return myString;
	}

	public void setMyString(String myString) {
		this.myString = myString;
	}

	public String[] getMyStrings() {
		return myStrings;
	}

	public void setMyStrings(String[] myStrings) {
		this.myStrings = myStrings;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

}