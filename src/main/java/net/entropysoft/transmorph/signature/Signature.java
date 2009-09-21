package net.entropysoft.transmorph.signature;

public abstract class Signature {

	public boolean isPrimitiveType() {
		return false;
	}

	public boolean isArrayType() {
		return false;
	}

	public boolean isClassType() {
		return false;
	}
	
	public boolean isTypeVar() {
		return false;
	}	
	
	public boolean isTypeArgument() {
		return false;
	}
	
	public abstract TypeSignature getTypeErasureSignature();
	
	/**
	 * get the signature using the "Internal Form of Fully Qualified Name"
	 * 
	 * @return
	 */
	public abstract String getSignature();	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getSignature().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Signature other = (Signature) obj;
		if (!getSignature().equals(other.getSignature()))
			return false;
		return true;
	}	
	
}
