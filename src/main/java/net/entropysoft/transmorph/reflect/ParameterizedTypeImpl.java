package net.entropysoft.transmorph.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public final class ParameterizedTypeImpl implements ParameterizedType {

	private final Class<?> rawType;
	private final Type[] actualTypeArguments;
	private final Type owner;

	public ParameterizedTypeImpl(Class<?> rawType, Type[] actualTypeArguments,
			Type owner) {
		this.rawType = rawType;
		this.actualTypeArguments = actualTypeArguments;
		this.owner = owner;
	}

	public Class<?> getRawType() {
		return rawType;
	}

	public Type[] getActualTypeArguments() {
		return actualTypeArguments;
	}

	public Type getOwnerType() {
		return owner;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ParameterizedType)) {
			return false;
		} else {
			// Check that information is equivalent
			ParameterizedType that = (ParameterizedType) o;
			if (this == that)
				return true;

			Type thatOwner = that.getOwnerType();
			Type thatRawType = that.getRawType();

			return (owner == null ? thatOwner == null : owner.equals(thatOwner))
					&& (rawType == null ? thatRawType == null : rawType
							.equals(thatRawType))
					&& Arrays.equals(actualTypeArguments, that
							.getActualTypeArguments());
		}
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(actualTypeArguments)
				^ (owner == null ? 0 : owner.hashCode())
				^ (rawType == null ? 0 : rawType.hashCode());
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (owner != null) {
			if (owner instanceof Class)
				sb.append(((Class) owner).getName());
			else
				sb.append(owner.toString());
			sb.append('.');
			if (owner instanceof ParameterizedType) {
				// Find simple name of nested type by removing the
				// shared prefix with owner.
				ParameterizedType parameterizedOwnerType = (ParameterizedType) owner;
				String simpleName = rawType.getName().replace(
						((Class) parameterizedOwnerType.getRawType()).getName()
								+ "$", "");
				sb.append(simpleName);
			} else {
				sb.append(rawType.getName());
			}
		} else {
			sb.append(rawType.getName());
		}

		if (actualTypeArguments != null && actualTypeArguments.length > 0) {
			sb.append("<");
			for (int i = 0; i < actualTypeArguments.length; i++) {
				Type type = actualTypeArguments[i];
				if (i > 0)
					sb.append(", ");
				if (type instanceof Class)
					sb.append(((Class) type).getName());
				else
					sb.append(type.toString());
			}

			sb.append(">");
		}
		return sb.toString();
	}

}
