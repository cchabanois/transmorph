package net.entropysoft.transmorph.type;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;

/**
 * <ul>
 * <li>? : upperBounds = [Object.class], lowerBounds = []</li>
 * <li>? extends N : upperBounds = [N.class], lowerBounds = []</li>
 * <li>? super N : upperBounds = [Object.class], lowerBounds = [N.class]</li>
 * </ul>
 * 
 * @author cedric
 * 
 */
public final class WildcardTypeImpl implements WildcardType {

	private final Type[] ub;
	private final Type[] lb;

	public WildcardTypeImpl(Type[] ub, Type[] lb) {
		this.ub = ub;
		this.lb = lb;
	}

	public Type[] getUpperBounds() {
		return ub;
	}

	public Type[] getLowerBounds() {
		return lb;
	}

	public int hashCode() {
		return Arrays.hashCode(lb) ^ Arrays.hashCode(ub);
	}

	public boolean equals(Object obj) {
		if (obj instanceof WildcardType) {
			WildcardType that = (WildcardType) obj;
			return Arrays.equals(that.getLowerBounds(), lb)
					&& Arrays.equals(that.getUpperBounds(), ub);
		}
		return false;
	}

	@Override
	public String toString() {
		// try to have the same result than Sun implementation
		StringBuilder sb = new StringBuilder();
		Type[] bounds = null;
		if (lb.length > 0) {
			sb.append("? super ");
			bounds = lb;
		} else if (ub.length > 0 && !ub[0].equals(Object.class)) {
			sb.append("? extends ");
			bounds = ub;
		} else {
			sb.append("?");
		}
		if (bounds != null) {
			for (int i = 0; i < bounds.length; i++) {
				Type type = bounds[i];
				if (i != 0) {
					sb.append(" & ");
				}
				if (type instanceof Class) {
					sb.append(((Class) type).getName());
				} else {
					sb.append(type.toString());
				}
			}
		}
		return sb.toString();
	}

}
