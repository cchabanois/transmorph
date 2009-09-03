package samples;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CloneableBean implements Cloneable {

	private String myString;
	private List<Date> myDates = new ArrayList<Date>();
	
	@Override
	public Object clone() {
		CloneableBean clone = null;
		try {
			clone = (CloneableBean)super.clone();
		} catch (CloneNotSupportedException e) {
		}
		clone.myDates = new ArrayList<Date>();
		for (Date date : myDates) {
			clone.myDates.add(new Date(date.getTime()));
		}
		return clone;
	}
	
	public void setMyDates(List<Date> myDates) {
		this.myDates = myDates;
	}
	
	public List<Date> getMyDates() {
		return myDates;
	}
	
	public void setMyString(String myString) {
		this.myString = myString;
	}
	
	public String getMyString() {
		return myString;
	}
	
}
