package samples;

import java.io.Serializable;

public class MyBeanBATransferObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8708374162088808414L;
	private MyBeanABTransferObject myBeanABTransferObject;
	private long myNumber;
	
	public MyBeanABTransferObject getMyBeanAB() {
		return myBeanABTransferObject;
	}

	public void setMyBeanAB(
			MyBeanABTransferObject myBeanABTransferObject) {
		this.myBeanABTransferObject = myBeanABTransferObject;
	}

	public long getMyNumber() {
		return myNumber;
	}

	public void setMyNumber(long myNumber) {
		this.myNumber = myNumber;
	}
	
}
