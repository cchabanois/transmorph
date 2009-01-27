package samples;

import java.io.Serializable;

public class MyBeanABTransferObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1249106229772393979L;
	
	private MyBeanBATransferObject myBeanBATransferObject;
	private int[] myIntegers;
	
	
	public MyBeanBATransferObject getMyBeanBA() {
		return myBeanBATransferObject;
	}

	public void setMyBeanBA(
			MyBeanBATransferObject myBeanBATransferObject) {
		this.myBeanBATransferObject = myBeanBATransferObject;
	}

	public int[] getMyIntegers() {
		return myIntegers;
	}

	public void setMyIntegers(int[] myIntegers) {
		this.myIntegers = myIntegers;
	}
	
	
}
