package net.entropysoft.transmorph.type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;


public final class GenericArrayTypeImpl implements GenericArrayType {

  private final Type genericComponentType;

  public GenericArrayTypeImpl(Type genericComponentType) {
    this.genericComponentType = genericComponentType;
  }

  public Type getGenericComponentType() {
    return genericComponentType;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof  GenericArrayType)) {
      return false;
    } else {
      GenericArrayType genericArrayType = (GenericArrayType) o;
      Type componentType = genericArrayType.getGenericComponentType();
      return genericComponentType == null ?
          componentType == null : genericComponentType.equals(componentType);
    }
  }

  @Override
  public int hashCode() {
    return (genericComponentType == null) ? 0 : genericComponentType.hashCode();
  }
  
  public String toString() {
      Type componentType = getGenericComponentType();
      StringBuilder sb = new StringBuilder();

      if (componentType instanceof Class)
          sb.append(((Class<?>) componentType).getName());
      else
          sb.append(componentType.toString());
      sb.append("[]");
      return sb.toString();
  }

  
}
