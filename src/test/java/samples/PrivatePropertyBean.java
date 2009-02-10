package samples;

public class PrivatePropertyBean {
	private int privateProperty;
	private int publicProperty;
	
	public PrivatePropertyBean(int privateProperty) {
		setPrivateProperty(privateProperty);
	}

	private int getPrivateProperty() {
		return privateProperty;
	}

	private void setPrivateProperty(int privateProperty) {
		this.privateProperty = privateProperty;
	}

	public int getPublicProperty() {
		return publicProperty;
	}

	public void setPublicProperty(int publicProperty) {
		this.publicProperty = publicProperty;
	}
	
}
