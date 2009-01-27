package samples;

import java.util.List;

public class MyBeanAB {
	private MyBeanBA myBeanBA;
	private Long id;
	private List<Integer> myIntegers;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MyBeanBA getMyBeanBA() {
		return myBeanBA;
	}

	public void setMyBeanBA(MyBeanBA myBeanBA) {
		this.myBeanBA = myBeanBA;
	}

	public List<Integer> getMyIntegers() {
		return myIntegers;
	}

	public void setMyIntegers(List<Integer> myIntegers) {
		this.myIntegers = myIntegers;
	}

	
	
}
