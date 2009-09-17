package net.entropysoft.transmorph.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class is used to pass full generics type information, and avoid problems
 * with type erasure (that basically removes most usable type references from
 * runtime Class objects). It is based on ideas from <a
 * href="http://gafter.blogspot.com/2006/12/super-type-tokens.html"
 * >http://gafter.blogspot.com/2006/12/super-type-tokens.html</a>, Additional
 * idea (from a suggestion made in comments of the article) is to require bogus
 * implementation of <code>Comparable</code> (any such generic interface would
 * do, as long as it forces a method with generic type to be implemented). to
 * ensure that a Type argument is indeed given.
 *<p>
 * Usage is by sub-classing: here is one way to instantiate reference to generic
 * type <code>List&lt;Integer></code>:
 * 
 * <pre>
 * TypeReference ref = new TypeReference&lt;List&lt;Integer&gt;&gt;() {
 * };
 *</pre>
 * 
 * which can be passed to methods that accept TypeReference.
 * 
 * This class comes from Jackson Json-processor (http://jackson.codehaus.org)
 * and has been modified a bit.
 * 
 * @param <T>
 */
public abstract class TypeReference<T> implements Comparable<TypeReference<T>> {
	private final Type type;

	protected TypeReference() {
		Type superClass = getClass().getGenericSuperclass();
		if (superClass instanceof Class) { // sanity check, should never happen
			throw new IllegalArgumentException(
					"TypeReference constructed without actual type information");
		}
		type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
	}

	public Type getType() {
		return type;
	}

	/**
	 * The only reason we define this method (and require implementation of
	 * <code>Comparable</code>) is to prevent constructing a reference without
	 * type information.
	 */
	public int compareTo(TypeReference<T> o) {
		return toString().compareTo(o.toString());
	}

	/**
	 * Hashcode for this object.
	 * 
	 * @return hashcode for this object.
	 */
	@Override
	public int hashCode() {
		return type.hashCode();
	}

	/**
	 * Method to test equality.
	 * 
	 * @return true if this object is logically equal to the specified object,
	 *         false otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof TypeReference<?>)) {
			return false;
		}
		TypeReference<?> t = (TypeReference<?>) o;
		return type.equals(t.type);
	}

	/**
	 * Returns a string representation of this object.
	 * 
	 * @return a string representation of this object.
	 */
	@Override
	public String toString() {
		return type.toString();
	}

}