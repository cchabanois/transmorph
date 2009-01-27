package samples;


public class MyBeanBA {
	private MyBeanAB myBeanAB;
	private Long id;
	private int myNumber;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MyBeanAB getMyBeanAB() {
		return myBeanAB;
	}

	public void setMyBeanAB(MyBeanAB myBeanAB) {
		this.myBeanAB = myBeanAB;
	}

	public int getMyNumber() {
		return myNumber;
	}

	public void setMyNumber(int myNumber) {
		this.myNumber = myNumber;
	}

}
