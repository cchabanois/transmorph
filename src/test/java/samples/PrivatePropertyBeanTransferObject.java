package samples;

import java.text.MessageFormat;

public class PrivatePropertyBeanTransferObject {
	private String privateProperty;
	private String publicProperty;

	public PrivatePropertyBeanTransferObject() {
		
	}
	
	public PrivatePropertyBeanTransferObject(String privateProperty) {
		setPrivateProperty(privateProperty);
	}

	private String getPrivateProperty() {
		return privateProperty;
	}

	private void setPrivateProperty(String privateProperty) {
		this.privateProperty = privateProperty;
	}

	public String getPublicProperty() {
		return publicProperty;
	}

	public void setPublicProperty(String publicProperty) {
		this.publicProperty = publicProperty;
	}

	@Override
	public String toString() {
		return MessageFormat.format("Public property:{0}\nPrivate property:{1}",
				publicProperty, privateProperty);
	}

	
	
}
